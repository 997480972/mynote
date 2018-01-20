package com.controller;



import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dao.CategoryDao;
import com.dto.CategoryRq;
import com.dto.CategoryRsData;
import com.dto.PageRs;
import com.entity.Category;
import com.util.AdapterFactory;

@RestController
@Api(tags={"分类接口"})
public class CategoryController {

	@Autowired
	private CategoryDao categoryDao;

	private AdapterFactory adapterFactory = AdapterFactory.getFactory();
	/**
	 * 查询所有分类
	 * @return
	 */
	@ApiOperation(value="查询所有分类", notes = "查询第一页，每页2000条")
	@RequestMapping(value = "/categorys", method = RequestMethod.GET)
	public PageRs<CategoryRsData> findAll() {
		Page<Category> page = categoryDao.findAll(new PageRequest(0, 2000));
		return new PageRs<CategoryRsData>(page, CategoryRsData.class);
	}

	// 查看category
	@ApiOperation(value = "根据id查找分类", notes = "id查询分类")
	@RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
	public CategoryRsData fetchCategory(@PathVariable("id") Integer id) {
		Category category = categoryDao.findById(id);
		System.out.println(category);
		if(null == category){
			return null;
		}
		return adapterFactory.getDestObject(category, CategoryRsData.class);
	}
	
	@ApiOperation(value = "根据名字查找分类", notes = "分类名查询分类")
	@RequestMapping(value = "/category/name/{name}", method = RequestMethod.GET)
	public CategoryRsData fetchCategoryByName(@PathVariable("name") String name) {
		Category category = categoryDao.findByName(name);
		System.out.println(category);
		if(null == category){
			return null;
		}
		CategoryRsData categoryRsData = adapterFactory.getDestObject(category, CategoryRsData.class);
		return categoryRsData ;
	}
	
	@ApiOperation(value = "保存分类", notes = "新增或更新")
	@RequestMapping(value = "/category", method = RequestMethod.POST)
	public PageRs<CategoryRsData> saveCategory(@Valid @RequestBody CategoryRq categoryRq, BindingResult bindingResult) {
		String msg = categoryRq.validate(bindingResult); //请求参数有效验证
		if(StringUtils.isNotBlank(msg)){
			PageRs<CategoryRsData> pageRs = new PageRs<CategoryRsData>(msg);
			pageRs.setCode(403);
			return pageRs;
		}
		Category resCategory = categoryDao.save(adapterFactory.getDestObject(categoryRq, Category.class));
		if(null == resCategory){
			return null;
		}
		PageRs<CategoryRsData> pageRs = new PageRs<CategoryRsData>(adapterFactory.getDestObject(resCategory, CategoryRsData.class));
		return pageRs;
	}
}
