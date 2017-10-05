<#include "head.ftl">
<div id="content" style="margin-top:50px;margin-bottom:100px;border:1px solid;width:auto;height:auto;" align="center">
	<div style="width:80%;height:auto;border:1px solid;">
		<div v-for="note in notes" style="border-radius: 5px;margin-top:10px;background-image:url(/image/login.png); background-repeat:no-repeat; width:33%;height: 300px;float:left;">
			<a style="text-decoration: none" v-bind:href="['/note/' + note.id]" target="blank">
				<div style="padding:20px;">
					<h2>{{note.title}}</h2>
					<div name="hoverDisplay" class="text-left" style="width:100%;height:50%;"><span style="display:none;">{{note.content.replace(/(<.*?>)*(&.*?;)*/ig, "").substring(0, 150)}}</span></div>
				</div>
			</a>
		</div>
	</div>
	<div class="well" style="margin-top:30px;width:80%;height:auto;clear:both">
	  <ul class="pagination">
	    <li><a href="#">上一页</a></li>
      	<li class="active"><a href="#">1</a></li>
	    <li class="disabled"><a href="#">2</a></li>
	    <li><a href="#">3</a></li>
	    <li><a href="#" class="disabled">下一页</a></li>
	  </ul>
	</div>
</div>
<#include "foot.ftl">
<script >
	new Vue({
	  el: '#content',
	  data: {
	    notes: ${notes}
	  }
	});
	//悬浮显示文本，移出则隐藏
	$("div[name='hoverDisplay']").each(function(){
		$(this).hover(
		  function () {
		  	$(this).children().toggle();
		  },
		  function () {
		  	$(this).children().toggle();
		  }
		);
	});
</script>