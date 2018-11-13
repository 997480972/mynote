<div id="head">
	<#include "head.ftl">
</div>
<div id="content" style="margin-bottom:100px;border:1px solid;width:auto;height:auto;" align="center">
	<div style="width:80%;height:auto;border:1px solid;">
		<div v-for="note in pageResult.pageData" style="border-radius: 5px;margin-top:10px;background-image:url(/image/login.png); background-repeat:no-repeat; width:33%;height: 300px;float:left;">
			<a style="text-decoration: none" v-bind:href="['/note/' + note.id]">
				<div style="padding:20px;">
					<h2>{{note.title}}</h2>
					<div name="hoverDisplay" class="text-left" style="width:100%;height:50%;"><span style="display:block;">{{note.content.replace(/(<.*?>)*(&.*?;)*/ig, "").substring(0, 150)}}</span></div>
				</div>
			</a>
		</div>
	</div>
	<div class="well" style="margin-top:30px;width:80%;height:auto;clear:both">
		<ul class="pagination">
		    <li v-bind:class="{disabled: pageResult.pageNumber == 1}"><a @click="prePage" href="javascript:void(0);">上一页</a></li>
		</ul>
	  	<ul class="pagination"  v-for="page in pageResult.pageTotal">
	  		<li v-bind:class="{active: pageResult.pageNumber == page}" ><a @click="setPageNumber(page)" href="javascript:void(0);">{{page}}</a></li>
	  	</ul>
	  	<ul class="pagination">
			<li v-bind:class="{disabled: pageResult.pageNumber == pageResult.pageTotal}"><a disabled="true" @click="nextPage" href="javascript:void(0);">下一页</a></li>
		</ul>&nbsp;
		共{{pageResult.dataTotal}}条，每页
		<select @change="changePageSize" v-model="pageResult.pageSize">
			<option>9</option>
			<option>12</option>
			<option>15</option>
			<option>18</option>
		</select>条
	</div>
</div>
<#include "foot.ftl">
<script >
	var mainVue = new Vue({
	  el: '#content',
	  data: {
	  	pageResult: ''
	  },
	  created: function(){
	  		var queryMap = {};
	  		if(headVue.activeName){
	  			queryMap.categoryName = headVue.activeName;
	  		}
	  		$.ajax({
	  			type: "POST",  
	            url:"/notes",  
	            data: {"pageNo": 1,"pageSize": 9,"queryMap":queryMap}, 
	            error: function(request) {  
	                alert("Connection error");  
	            },  
	            success: function(data) {  
	                mainVue.pageResult = data;
	            }  
	  		});
	  		
	  },
	  /*
	  updated:function(){
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
	  },
	  */
	  methods:{
	  	changePageSize:function(event){  //选择每页条数
	  		var queryMap = {};
	  		if(headVue.activeName){
	  			queryMap.categoryName = headVue.activeName;
	  		}
	  		$.ajax({
	  			type: "POST",  
	            url:"/notes",  
	            data: {"pageNo": this.pageResult.pageNumber,"pageSize": this.pageResult.pageSize,"queryMap":queryMap}, 
	            error: function(request) {  
	                alert("Connection error");  
	            },  
	            success: function(data) {  
	                mainVue.pageResult = data;
	            }  
	  		});
	  	},
	  	setPageNumber:function(page){ //选择当前页
	  		var queryMap = {};
	  		if(headVue.activeName){
	  			queryMap.categoryName = headVue.activeName;
	  		}
	  		$.ajax({
	  			type: "POST",  
	            url:"/notes",  
	            data: {"pageNo": page,"pageSize":mainVue.pageResult.pageSize,"queryMap":queryMap}, 
	            error: function(request) {  
	                alert("Connection error");  
	            },  
	            success: function(data) {  
	                console.log(data);
	                mainVue.pageResult = data;
	            }  
	  		});
	  	},
	  	prePage:function(){ //上一页
	  		var queryMap = {};
	  		if(headVue.activeName){
	  			queryMap.categoryName = headVue.activeName;
	  		}
	  		if(mainVue.pageResult.pageNumber == 1){
	  			alert("没有上一页了！");
	  			return false;
	  		}
	  		$.ajax({
	  			type:"post",
	  			url:"/notes",
	  			data: {"pageNo":mainVue.pageResult.pageNumber - 1, "pageSize":mainVue.pageResult.pageSize,"queryMap":queryMap},
	  			error: function(request) {  
	                alert("Connection error");  
	            },  
	            success: function(data) {  
	                console.log(data);
	                mainVue.pageResult = data;
	            }  
	  		});
	  	},
	  	nextPage:function(){ //下一页
	  		var queryMap = {};
	  		if(headVue.activeName){
	  			queryMap.categoryName = headVue.activeName;
	  		}
	  		if(mainVue.pageResult.pageNumber == mainVue.pageResult.pageTotal){
	  			alert("没有下一页了！");
	  			return false;
	  		}
	  		$.ajax({
	  			type:"post",
	  			url:"/notes",
	  			data: {"pageNo":mainVue.pageResult.pageNumber + 1, "pageSize":mainVue.pageResult.pageSize,"queryMap":queryMap},
	  			error: function(request) {  
	                alert("Connection error");  
	            },  
	            success: function(data) {  
	                mainVue.pageResult = data;
	            }  
	  		});
	  	},
	  }
	});
	
</script>