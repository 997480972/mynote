<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript">
	$(function(){
		$('#layout_west_menu_tree').tree({
			url: '${pageContext.request.contextPath }/sys/menus',
			parentField: 'pid',
			onLoadSuccess: function(){
				//折叠菜单
				//$(this).tree('collapseAll');
			},
			onClick: function(node){;
				if(node.url){
					//点击树菜单,在主面板上添加一个tabs选项卡.
					addTabToCenterTabs({
						title: node.text,
						iconCls: node.iconCls,
						/*
						 * 引入页面两种方式:
					     * 1. href 方式 (推荐使用)
					     * href方式引入的页面必须是代码片段, 只加载body中的内容.
					     * url对应的页面就只需要body内的标签即可.
					     * 2. content 方式
					     * content 方式可以用 iframe 引入独立完整页面.
						 */
						//content:'<iframe src="${pageContext.request.contextPath}' + node.url + '" frameborder="0" style="border:0;width:100%;height:99%"></iframe>',
						href: '${pageContext.request.contextPath}' + node.url,
						closable: true
					});
				}
			}
		});
	});
	
	//添加tabs组件到center主面板
	function addTabToCenterTabs(opts){
		var centerTabs = $('#layout_main_centerTabs');
		if(centerTabs.tabs('exists', opts.title)){
			centerTabs.tabs('select', opts.title);
		}else{
			centerTabs.tabs('add', opts);
		}
	}
</script>
<div data-options="region:'west',title:'功能导航',iconCls:'application_side_boxes',split:true" style="width:200px;">
	<div id="aa" class="easyui-accordion" data-options="fit:true,border:false">   
	    <div title="系统菜单" data-options="iconCls:'application_home'">   
	        <ul id="layout_west_menu_tree" class="well well-sm"></ul>
	    </div>   
	    <div title="个人文件夹" data-options="iconCls:'folder_user'"></div>
	</div> 
</div>
