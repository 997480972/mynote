package com.hystrix;



import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dto.PageRq;
import com.dto.PageRs;
import com.entity.Note;
import com.hystrix.impl.NoteHystrixImpl;

@FeignClient(value = "mynote-service", fallback = NoteHystrixImpl.class)
public interface NoteHystrix {
    
	@RequestMapping(value = "/notes")
	PageRs<Note> findAll(PageRq pageRq);
	
    
    @RequestMapping(value = "/note/{id}", method = RequestMethod.GET)
    Note findOne(@RequestParam(value = "id") Integer id);
    
    @RequestMapping(value = "/note", method = RequestMethod.POST)
    Note save(Note note);
}

