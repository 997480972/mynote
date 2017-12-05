package com.hystrix;



import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Category;
import com.entity.Note;
import com.hystrix.impl.NoteHystrixImpl;
import com.util.PageParam;
import com.util.PageResult;

@FeignClient(value = "mynote-service")
public interface CategoryHystrix {
    
	@RequestMapping(value = "/categorys")
	List<Category> findAll();
	
    @RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
    Category findOne(@RequestParam(value = "id") Integer id);
    
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    Category save(Category category);
}

