package com.dto;


import io.swagger.annotations.ApiModelProperty;


import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分类请求模型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRq extends BaseRequest {
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty("id")
	private Integer id;  //id
	
	@NotBlank
	@Size(min=1,max=50)
    @ApiModelProperty("分类名称")
    private String name; //分类名称
    
    @ApiModelProperty("创建时间")
    private String createTime;    //创建时间
}
