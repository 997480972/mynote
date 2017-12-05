package com.util;

import org.apache.commons.lang.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
		/**
		 * 对象转换成JSON
		 * @param obj 对象(包括实体对象,Map<String,Object>和List等)
		 * @return
		 */
		public static String object2Json(Object obj){
			String json = null;
			try {
				ObjectMapper mapper = new ObjectMapper();
				json = mapper.writeValueAsString(obj);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return json;
		}

		/**
		 * json格式数据转换成对象(对象包括Bean对象，Map和List等)
		 * @param json json格式的字符串
		 * @param clazz 转换类
		 * @return
		 */
	public static Object json2Object(String json, Class<? extends Object> clazz) {
		Object object = null;
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		try {
			object = objectMapper.readValue(json, clazz);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return object;
	}
	
	 public static Object fromJson(String requestStr, Class<?> collectionClazz, Class<?>... elementClazzes) {
	        if (StringUtils.isEmpty(requestStr)) {
	            return null;
	        }
	        ObjectMapper objectMapper = new ObjectMapper();
	        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
	        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

	        Object object = null;
	        try {
	            JavaType javaType = objectMapper.getTypeFactory().
	                constructParametricType(collectionClazz, elementClazzes);

	            object = objectMapper.readValue(requestStr, javaType);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	        return object;
	    }
}
