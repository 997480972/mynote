<#include "/layout/head.ftl">
<div id="content" style="margin-top:50px;margin-bottom:100px;width:auto;height:auto;" align="center">
	<h2>分类：{{note.categoryName}}</h2>
	<div style="width:80%;height:auto;border:1px solid;text-align:left;">
		<div style="padding:20px;">
			<h2 align="center">{{note.title}}</h2>
			<div v-html="note.content"></div>
		</div>
	</div>
	<br/>
	<a class="btn btn-info" :href="'/noteEdit?id=' + note.id">Edit Note</a>
</div>
<#include "/layout/foot.ftl">
<script >
	new Vue({
	  el: '#content',
	  data: {
	    note: ''
	  },
	  created: function(){
	    this.note = ${note};
	  	headVue.activeName = this.note.categoryName;
	  }
	  
  });
</script>