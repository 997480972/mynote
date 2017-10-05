package com.dao;

import org.springframework.data.repository.CrudRepository;

import com.entity.Note;


public interface NoteDao extends CrudRepository<Note, Integer> {

}
