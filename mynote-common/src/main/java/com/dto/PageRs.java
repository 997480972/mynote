package com.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.domain.Page;

import com.util.AdapterFactory;

/**
 * 分页响应传输
 * @param <T> 内容数据类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRs<T> extends BaseResponse {

	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "返回的数据内容")
	private List<T> pageData = new ArrayList<T>();

	public PageRs(String message) {
		this.message = message;
	}
	/**
	 * 内容数据构造器
	 * @param t 数据类型
	 */
	public PageRs(T t) {
		this.pageData.add(t);
	}
	/**
	 * 分页数据响应构造器
	 * @param page 查出的分页数据
	 * @param tClass 传输响应对象class
	 */
	public <E>PageRs(Page<E> page, Class<T> tClass){
		this.setDataTotal((int)page.getTotalElements());
		this.setPageNumber(page.getNumber() + 1);
		this.setPageSize(page.getSize());
		this.setPageTotal(page.getTotalPages());
		AdapterFactory adapterFactory = AdapterFactory.getFactory();
		try {
			List<E> entityList = page.getContent();
			this.pageData = adapterFactory.getDestObjectList(entityList, tClass); //(反射获取泛型把实体转换成响应传输数据对象，封装List)
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
