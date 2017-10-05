<link rel="stylesheet" href="/css/bootstrap.min.css">
<script src="/js/jquery.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/vue.min.js"></script>
<title>Note</title>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
	<div class="container">
		<div class="navbar-header">
			<a class="navbar-brand" href="/">Note</a>
		</div class="navbar-header">
		<div  class="navbar-right" >
			<a id="addNote" class="btn btn-info navbar-btn" href="/noteEdit">
	            Add Note
	        </a>
		</div>
		<div>
			<ul class="nav navbar-nav">
				<li class="active"><a href="/">Home</a></li>
				<li><a href="#">SVN</a></li>
				<li><a href="#">iOS</a></li>
				<li><a href="#">VB.Net</a></li>
				<li class="dropdown">
					<a class="dropdown-toggle" data-toggle="dropdown" href="#">
						Java <span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li><a href="#">Swing</a></li>
						<li><a href="#">jMeter</a></li>
						<li><a href="#">EJB</a></li>
						<li class="divider"></li>
						<li><a href="#">分离的链接</a></li>
					</ul>
				<li><a href="#">PHP</a></li>
				<li><div class="btn-group">
						<button type="button" class="btn btn-primary dropdown-toggle btn-lg" data-toggle="dropdown">默认
					        <span class="caret"></span>
					    </button>
					    <ul class="dropdown-menu" role="menu">
					        <li>
					            <a href="#">功能</a>
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
<div id="messageDiv" class="alert alert-success alert-dismissable" style="margin-top:50px;display:none;" >
    <button type="button" class="close" data-dismiss="alert"
            aria-hidden="true">
        &times;
    </button>
    {{message}}
</div>
<script>
	var vu = new Vue({
				  el: '#messageDiv',
				  data: {
				    message: ''
				  }
			});
</script>