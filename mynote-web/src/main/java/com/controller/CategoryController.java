package com.controller;



import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.entity.Category;
import com.hystrix.CategoryHystrix;
import com.util.JsonUtils;


@Controller
@Slf4j
public class CategoryController {
	
	@Value("${server.port}")
	String port;
	
	@Autowired
	private CategoryHystrix categoryHystrix;
	
	@ResponseBody
	@RequestMapping("/categorys")
	public List<Category> getAllCategorys(){
		return categoryHystrix.findAll();
	}
	//保存
	@ResponseBody
	@RequestMapping(value="/category", method=RequestMethod.POST)
	public Category saveCategory(Category category){
		return categoryHystrix.save(category);
	}
}
