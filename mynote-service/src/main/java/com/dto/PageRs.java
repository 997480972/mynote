package com.dto;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页响应传输
 * @param <K>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRs<T> extends BaseResponse {

	private static final long serialVersionUID = 1L;
	
	private List<T> pageData = new ArrayList<T>();

	public PageRs(String message) {
		this.message = message;
	}
	/**
	 * 分页数据响应构造器
	 * @param page 查出的分页数据
	 * @param entityClass 实体class
	 * @param tClass 传输响应对象class
	 */
	public <E>PageRs(Page<E> page,  Class<E> entityClass, Class<T> tClass){
		this.setDataTotal((int)page.getTotalElements());
		this.setPageNumber(page.getNumber() + 1);
		this.setPageSize(page.getSize());
		this.setPageTotal(page.getTotalPages());
		Constructor<T> constructor = null;
		try {
			constructor = tClass.getConstructor(entityClass); //获取传输响应对象的构造器（参数为实体）
			List<E> entityList = page.getContent();
			for (E entity : entityList) { //封装页面响应数据list
				this.pageData.add(constructor.newInstance(entity)); //(反射获取泛型把实体转换成响应传输数据对象)
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
