package com.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * 所有请求响应的基类
 * @author liu
 */
@Data
public abstract class BaseResponse implements Serializable{
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "返回时间戳", dataType = "long")
	private Long timestamp = new Date().getTime();
	@ApiModelProperty(value = "错误代码(成功则返回200)", dataType = "int")
	protected int code = 200; 
	@ApiModelProperty(value = "详细信息（可能是错误信息、提示信息）", dataType = "String")
	protected String message; 
	@ApiModelProperty(value = "版本号", dataType = "String")
	private String version="1.0";
	
	@ApiModelProperty(value = "每页多少条")
	protected int pageSize = 20; 
	@ApiModelProperty(value = "第几页")
	protected int pageNumber = 1;
	@ApiModelProperty(value = "总数据")
	protected int dataTotal = 0; 
	@ApiModelProperty(value = "总页数")
	protected int pageTotal = 0; 
	
	/**
	 * 获取总页数
	 * @return
	 */
	public int getPageTotal(){
		return (dataTotal - 1) / pageSize + 1;
	}
}
