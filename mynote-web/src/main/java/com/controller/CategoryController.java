package com.controller;



import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dto.PageRs;
import com.entity.Category;
import com.hystrix.CategoryHystrix;


@Controller
@Slf4j
public class CategoryController {
	
	@Value("${server.port}")
	String port;
	
	@Autowired
	private CategoryHystrix categoryHystrix;
	
	
	//查询所有分类
	@ResponseBody
	@RequestMapping("/categorys")
	public List<Category> getAllCategorys(){
		System.out.println("查询所有分类");
		PageRs<Category> pageRs = categoryHystrix.findAll();
		System.out.println(pageRs);
		return pageRs.getPageData();
	}
	//保存
	@ResponseBody
	@RequestMapping(value="/category", method=RequestMethod.POST)
	public String saveCategory(Category category){
		String msg = "save success!";
		if(null == category){
			return null;
		}
		if(null == category.getId()){ //add
			if(null != categoryHystrix.findOne(category.getName())){
				return category.getName() + " exist";
			}
		}
		categoryHystrix.save(category);
		return msg;
	}
}
