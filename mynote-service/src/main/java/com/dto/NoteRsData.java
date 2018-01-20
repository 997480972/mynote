package com.dto;


import java.io.Serializable;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 笔记传输响应数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteRsData implements Serializable{

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("id")
	private Integer id;  //文章id
	
	@ApiModelProperty("分类id")
    private Integer categoryId; //文章分类id

	@ApiModelProperty("分类名称")
    private String categoryName; //分类名称
    
	@ApiModelProperty(value="标题",required=true)
    private String title;   //标题
    
	@ApiModelProperty("内容")
    private String content;  //内容

	@ApiModelProperty(value="描述",required=false)
    private String description; //描述

	@ApiModelProperty("状态")
    private Integer status;  //状态

	@ApiModelProperty("作者")
	private String author; //作者
	
	@ApiModelProperty("创建时间")
    private String createTime;    //创建时间

	@ApiModelProperty("更新时间")
    private String updateTime;    //更新时间

	@ApiModelProperty("浏览数")
    private Integer showCount;  //浏览数
	
}
