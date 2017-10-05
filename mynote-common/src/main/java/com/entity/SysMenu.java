package com.entity;

/**
 * 菜单模型 - Tree控件
 * Menu - SysMenu
 * @author Administrator
 */
public class SysMenu {
	
	private Integer id;
	private String text;
	private String url;
	private Integer pid;
	private String iconCls;
	private Integer order;
	private String remark;
	
	//EasyUI 使用 children 来封装层级Tree
	//通过扩展pid插件方式来实现平滑树(类似zTree), 就不需要 children.
//	private List<Menu> children;
	
	//EasyUI 使用 attributes 类封装 Tree的其它属性
	//如 url, 在 1.4 之后不需要封装在 attributes 中.
//	private List<String> attributes;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
