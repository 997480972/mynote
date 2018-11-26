package com.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.entity.Category;

@Mapper
public interface CategoryMapper {
	 @Select("select id, name, create_time as createTime from category where id = #{id}")
	 Category findById(@Param("id") int id);
}
