package com.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.interceptor.AuthorizedInterceptor;

/**
 * 配置类
 * @author Administrator
 */
@SpringBootConfiguration
public class MySpringMVCConfig extends WebMvcConfigurerAdapter{

	@Autowired
	private AuthorizedInterceptor authorizedInterceptor;
	
	/**
	 * 拦截器配置
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//拦截所有请求
		registry.addInterceptor(authorizedInterceptor).addPathPatterns("/**");
	}
}
