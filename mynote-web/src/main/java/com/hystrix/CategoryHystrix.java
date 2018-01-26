package com.hystrix;




import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dto.PageRs;
import com.entity.Category;

@FeignClient(value = "mynote-service")
public interface CategoryHystrix {
    
	@RequestMapping(value = "/categorys")
	PageRs<Category> findAll();
	
    @RequestMapping(value = "/category/{id}", method = RequestMethod.GET)
    Category findOne(@RequestParam(value = "id") Integer id);
    
    @RequestMapping(value = "/category/name/{name}", method = RequestMethod.GET)
    Category findOne(@RequestParam(value = "name") String name);
    
    @RequestMapping(value = "/category", method = RequestMethod.POST)
    Category save(Category category);
}

