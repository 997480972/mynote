package com.hystrix;



import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.entity.Note;
import com.hystrix.impl.NoteHystrixImpl;
import com.util.PageParam;
import com.util.PageResult;

@FeignClient(value = "mynote-service", fallback = NoteHystrixImpl.class)
public interface NoteHystrix {
    
	@RequestMapping(value = "/")
	PageResult<Note> findAll(PageParam<Note> pageParam);
	
    
    @RequestMapping(value = "/note/{id}", method = RequestMethod.GET)
    Note findOne(@RequestParam(value = "id") Integer id);
    
    @RequestMapping(value = "/note", method = RequestMethod.POST)
    Note save(Note note);
}

