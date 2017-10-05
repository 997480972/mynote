<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> 
<script type="text/javascript" charset="utf-8">
	$(function() {

		$('#layout_east_calendar').calendar({
			fit : true,
			current : new Date(),
			border : false,
			onSelect : function(date) {
				$(this).calendar('moveTo', new Date());
			}
		});

		$('#layout_east_onlinePanel').panel({
			tools : [ {
				iconCls : 'database_refresh',
				handler : function() {
				}
			} ]
		});

	});
</script>
<div data-options="region:'east',title:'日历',iconCls:'date'" style="width:200px;">
	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'north',border:false" style="height: 180px; overflow: hidden;">
			<div id="layout_east_calendar"></div>
		</div>
		<div data-options="region:'center',border:false" style="overflow: hidden;">
			<div id="layout_east_onlinePanel" data-options="fit:true,border:false,title:'当前在线人员',iconCls:'status_online'">
				<div class="well">
				</div>
			</div>
		</div>
	</div>
</div>