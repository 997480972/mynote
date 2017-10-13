package com.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Note;
import com.util.PageResult;


public interface NoteDao extends JpaRepository<Note, Integer> {

	public Note findById(Integer id);
	
	public Page<Note> findAllByCategoryName(String categoryName, Pageable pageable);
}
