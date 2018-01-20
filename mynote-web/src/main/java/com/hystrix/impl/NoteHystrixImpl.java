package com.hystrix.impl;

import org.springframework.stereotype.Component;

import com.dto.PageRq;
import com.dto.PageRs;
import com.entity.Note;
import com.hystrix.NoteHystrix;

/**
 *熔断器
 */
@Component
public class NoteHystrixImpl implements NoteHystrix {

	@Override
	public PageRs<Note> findAll(PageRq pageRq) {
		System.out.println("hystrix");
		return null;
	}


	@Override
	public Note save(Note note) {
		// TODO Auto-generated method stub
		System.out.println( "save fail...");
		return null;
	}

	@Override
	public Note findOne(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
