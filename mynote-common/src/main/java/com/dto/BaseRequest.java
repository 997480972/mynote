package com.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.validation.BindingResult;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 所有请求参数的基类
 * @author liu
 */
@Data
@NoArgsConstructor
public abstract class BaseRequest implements Serializable{
	private static final long serialVersionUID = 1L;
	@ApiModelProperty(value = "必须参数,请求时间戳，安全校验使用", dataType = "Long", required=true)
	private Long timestamp = new Date().getTime();
	@ApiModelProperty(value = "可选参数 当前第几页(默认第一页)", dataType = "int")
	private int pageNo = 1;
	@ApiModelProperty(value = "可选参数 每页记录数（最大每页返回5000条数据）", dataType = "int")
	private int pageSize = 5000;
	@ApiModelProperty(value = "可选参数 排序字段 默认是id")
	private String sort = "id";
	@ApiModelProperty(value = "可选参数 排序方式  0 降序  1升序  默认0")
	private int direction = 0;  

	/**
	 * 请求参数有效验证
	 * @param bindingResult 验证结果
	 * @return 验证错误信息
	 */
	public String validate(BindingResult bindingResult) {
		if(null != bindingResult){
			StringBuilder msgSb = new StringBuilder();
			if(bindingResult.hasErrors()){
				bindingResult.getFieldErrors().forEach(fieldError -> {
					msgSb.append(fieldError.getField() + ":" +fieldError.getDefaultMessage() + "; ");
				});
				return msgSb.toString();
			}
		}
		return null;
	}
	
	/**
	 * 获取JPA排序方式
	 * @return
	 */
	public Direction getDescOrAsc(){
		Direction d = Direction.DESC;
		if(this.getDirection() == 1) {
			d = Direction.ASC;
		}
		return d;
	}
}
