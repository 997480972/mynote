package com.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章实体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name="note")
public class Note implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;  //文章id
	
    @Column(name="category_id")
    private Integer categoryId; //文章分类id

    @Column(name="category_name")
    private String categoryName; //分类名称
    
    @Column(nullable = false)
    private String title;   //标题
    
    @Column(columnDefinition="text", nullable = false)
    private String content;  //内容

    private String description; //描述

    private Integer status;  //状态

	private String author; //作者
	
    @Column(name="create_time", columnDefinition="timestamp")
    private String createTime;    //创建时间

    @Column(name="update_time", length=64)
    private String updateTime;    //更新时间

    @Column(name="show_count")
    private Integer showCount;  //浏览数
}
