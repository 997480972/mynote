package com.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.entity.Category;


public interface CategoryDao extends JpaRepository<Category, Integer> {

	public Category findById(Integer id);
}
