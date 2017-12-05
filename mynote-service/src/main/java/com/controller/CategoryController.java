package com.controller;



import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dao.CategoryDao;
import com.entity.Category;

@RestController
@Api("分类")
public class CategoryController {

	@Autowired
	private CategoryDao categoryDao;

	/**
	 * 查询所有分类
	 * @return
	 */
	@ApiOperation("查询所有分类")
	@RequestMapping(value = "/categorys", method = RequestMethod.GET)
	public List<Category> findAll() {
		List<Category> categories = categoryDao.findAll();
		System.out.println(categories.size());
		return categories;
	}

	// 查看category
	@RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
	public Category fetchCategory(@PathVariable("id") Integer id) {
		Category category = categoryDao.findById(id);
		System.out.println(category);
		return category;
	}

	// save category
	@RequestMapping(value = "/category", method = RequestMethod.POST)
	public Category saveCategory(@RequestBody Category category) {
		return categoryDao.save(category);
	}
}
