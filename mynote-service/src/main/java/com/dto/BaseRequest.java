package com.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.Sort.Direction;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 所有请求参数的基类
 * @author liu
 */
@Data
@NoArgsConstructor
public abstract class BaseRequest<T> implements Serializable{
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
	 * 验证请求参数
	 * @return 验证错误信息
	 */
	public abstract String validate();
	
	/**
	 * 复制属性
	 * @param t 目的对象
	 * @return 属性赋值后目的对象
	 */
	public T copyProperties(T t){
		try {
			BeanUtils.copyProperties(t,this);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return t;
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
