package com.dto;


import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;


import org.apache.commons.beanutils.BeanUtils;

import com.entity.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分类响应模型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRsData implements Serializable {
	private static final long serialVersionUID = 1L;

	
	private Integer id;  //id
	
    @ApiModelProperty("分类名称")
    private String name; //分类名称
    
    @ApiModelProperty("创建时间")
    private String createTime;    //创建时间
    
}
