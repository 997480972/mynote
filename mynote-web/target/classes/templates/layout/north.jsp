<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script type="text/javascript">
	
	//更换主题皮肤
	function changeThemeFun(themeName) {
		if ($.cookie('easyuiThemeName')) {
			$('#layout_north_themeMenu').menu('setIcon', {
				target : $('#layout_north_themeMenu div[title=' + $.cookie('easyuiThemeName') + ']')[0],
				iconCls : 'emptyIcon'
			});
		}
		$('#layout_north_themeMenu').menu('setIcon', {
			target : $('#layout_north_themeMenu div[title=' + themeName + ']')[0],
			iconCls : 'tick'
		});
	
		var $easyuiTheme = $('#easyuiTheme');
		var url = $easyuiTheme.attr('href');
		var href = url.substring(0, url.indexOf('themes')) + 'themes/' + themeName + '/easyui.css';
		$easyuiTheme.attr('href', href);
	
		//tabs如果是以content方式, 则要设置, 否则通过iframe嵌入的页面无法更换主题皮肤.
		var $iframe = $('iframe');
		if ($iframe.length > 0) {
			for ( var i = 0; i < $iframe.length; i++) {
				var ifr = $iframe[i];
				try {
					$(ifr).contents().find('#easyuiTheme').attr('href', href);
				} catch (e) {
					try {
						ifr.contentWindow.document.getElementById('easyuiTheme').href = href;
					} catch (e) {
					}
				}
			}
		}
	
		$.cookie('easyuiThemeName', themeName, {
			path : '/',
			expires : 7
		});
	};
	
	//个人资料
	function profile(){
		$('<div>').dialog({
			width: 400,
			height: 280,
			method: 'get',
			href: '${pageContext.request.contextPath }/sys/user/${sessionScope.user.id}',
			modal: true,
			title: '个人资料',
			iconCls: 'icon-man',
			onClose: function(){
				$(this).dialog('destroy');
			}
		});
	}
	
	//修改个人资料
	function modifyProfile(){
		
	}
	
	//修改密码
	function changePwd(){
		
	}
	
	$(function(){
		//注销
		$('#layout_north_logout').click(function(){
			$.getJSON('${pageContext.request.contextPath}/sys/logout',
					   {date: new Date()},
					   function(r){
						   if(r.success){
							   location.replace('${pageContext.request.contextPath}/login.jsp');
						   }
			});
		});
	});
</script>
<div data-options="region:'north',border:false" style="height:60px;background:#E7F0FF">
	<img alt="" src="${pageContext.request.contextPath }/images/logo.png">
	<div style="position: absolute; left: 310px; top: 10px;">
		<a href="javascript:void(0);" class="easyui-linkbutton" 
			data-options="iconCls:'icon-man',plain:true"><span>${sessionScope.user.name }</span></a>
	</div>
	<div style="position: absolute; left: 400px; top: 10px;">
		<a href="javascript:void(0);" class="easyui-menubutton" 
			data-options="menu:'#layout_north_themeMenu',iconCls:'theme'"><span>换肤</span></a>
	</div>
	<div style="position: absolute; left: 470px; top: 10px;">
		<a href="javascript:void(0);" class="easyui-menubutton" 
			data-options="menu:'#layout_north_set',iconCls:'cog'"><span>设置</span></a>
	</div>
	<div style="position: absolute; left: 540px; top: 10px;">
		<a id="layout_north_logout" href="javascript:void(0);" class="easyui-linkbutton" 
			data-options="iconCls:'logout',plain:true"><span>注销</span></a>
	</div> 
</div>
<div id="layout_north_themeMenu" style="width: 180px; display: none;">
	<div onclick="changeThemeFun('default');" title="default">default</div>
	<div onclick="changeThemeFun('bootstrap');" title="bootstrap">bootstrap</div>
	<div onclick="changeThemeFun('material');" title="material">material</div>
	<div onclick="changeThemeFun('gray');" title="gray">gray</div>
	<div onclick="changeThemeFun('metro');" title="metro">metro</div>
	<div onclick="changeThemeFun('black');" title="black">black</div>
	<div class="menu-sep"></div>
	<div onclick="changeThemeFun('cupertino');" title="cupertino">cupertino</div>
	<div onclick="changeThemeFun('dark-hive');" title="dark-hive">dark-hive</div>
	<div onclick="changeThemeFun('pepper-grinder');" title="pepper-grinder">pepper-grinder</div>
	<div onclick="changeThemeFun('sunny');" title="sunny">sunny</div>
	<div class="menu-sep"></div>
	<div onclick="changeThemeFun('metro-blue');" title="metro-blue">metro-blue</div>
	<div onclick="changeThemeFun('metro-gray');" title="metro-gray">metro-gray</div>
	<div onclick="changeThemeFun('metro-green');" title="metro-green">metro-green</div>
	<div onclick="changeThemeFun('metro-orange');" title="metro-orange">metro-orange</div>
	<div onclick="changeThemeFun('metro-red');" title="metro-red">metro-red</div>
</div>
<div id="layout_north_set" style="width: 180px; display: none;">
	<div onclick="profile();" title="个人资料" data-options="iconCls:'icon-man'">个人资料</div>
	<div onclick="modifyProfile();" title="个人资料修改">个人资料修改</div>
	<div class="menu-sep"></div>
	<div onclick="changePwd();" title="账户密码修改" data-options="iconCls:'key'">个人密码修改</div>
</div>
</html>