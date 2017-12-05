<link rel="stylesheet" href="/css/bootstrap.min.css">
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/vue.min.js"></script>
<script src="/plugins/tips/layer.js"></script>
<title>Note</title>
<nav class="navbar navbar-inverse navbar-static-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="/">Note</a>
		</div class="navbar-header">
		<div  class="navbar-right" >
			<a id="addNote" class="btn btn-info navbar-btn" href="/noteEdit">
	            Add Note
	        </a>
		</div>
		<div id="menu">
			<ul class="nav navbar-nav">
				<li class="active"><a href="/">Home</a></li>
				<ul class="nav navbar-nav" v-for="category in menus">
					<li><a href="javascript:void(0);">{{category.name}}</a></li>
				</ul>
				<li class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown" href="#">
						Java <span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li class="divider"></li>
						<li><a href="#">分离的链接</a></li>
					</ul>
				</li>
				<li><div class="btn-group">
						<button type="button" class="btn btn-primary dropdown-toggle btn-lg" data-toggle="dropdown">分类操作
					        <span class="caret"></span>
					    </button>
					    <ul class="dropdown-menu" role="menu">
					        <li>
					        	<input type="text"  class="form-control" style="text-align:center;" name="categoryName" placeholder="请输入分类">
					            <a href="#">新增分类</a>
					        </li>
					        <li>
					            <a href="#">另一个功能</a>
					        </li>
					        <li>
					            <a href="#">其他</a>
					        </li>
					        <li class="divider"></li>
					        <li>
					            <a href="#">分离的链接</a>
					        </li>
					    </ul>
					</div>
				</li>
			</ul>
		</div>
	</div>
</nav>
<script>
	vue = new Vue({
		  el: '#menu',
		  data: {
		    menus: ''
		  },
		  created:function(){
		  	$.ajax({
	  			type: "GET",  
	            url:"/categorys",  
	            error: function(request) {  
	                alert("Connection error");  
	            },  
	            success: function(data) {  
	            	vue.menus = data;
	            }  
	  		});
		  }
	});
</script>