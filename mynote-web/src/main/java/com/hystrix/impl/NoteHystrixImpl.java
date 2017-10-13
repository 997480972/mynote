package com.hystrix.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.entity.Note;
import com.hystrix.NoteHystrix;
import com.util.PageParam;
import com.util.PageResult;

/**
 *熔断器
 */
@Component
public class NoteHystrixImpl implements NoteHystrix {

	@Override
	public PageResult<Note> findAll(PageParam<Note> pageParam) {
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
