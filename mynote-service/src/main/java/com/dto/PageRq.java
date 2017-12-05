package com.dto;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页请求传输
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRq<T> extends BaseRequest<T> {

	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> map = new HashMap<String, Object>();
	
	@Override
	public String validate() {
		return null;
	}
}
