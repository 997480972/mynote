package com.dto;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.validation.BindingResult;

import com.entity.Note;
import com.netflix.infix.lang.infix.antlr.EventFilterParser.null_predicate_return;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 笔记传输对象
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteRq extends BaseRequest {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty("id")
	private Integer id;  //文章id
	
	@ApiModelProperty("分类id")
    private Integer categoryId; //文章分类id

	@ApiModelProperty("分类名称")
    private String categoryName; //分类名称
    
	@NotBlank
	@Size(min=1,max=50)
	@ApiModelProperty(value="标题",required=true)
    private String title;   //标题
    
	@NotBlank
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
