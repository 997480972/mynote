package com;

import lombok.extern.slf4j.Slf4j;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import com.dao.NoteDao;
import com.entity.Note;


@SpringBootApplication
@MapperScan("com.dao")
@Slf4j
public class Application extends SpringBootServletInitializer{
	 public static void main(String[] args) {
		 System.out.println(" springApplication run !");
		 SpringApplication.run(Application.class,args);
	}
//	@Bean
//	public CommandLineRunner demo(NoteDao noteDao) {
//		return (args) -> {
//			// save a couple of customers
//			noteDao.save(new Note());
//
//			// fetch all customers
//			log.info("Customers found with findAll():");
//			log.info("------------------------------");
//			for (Note note : noteDao.findAll()) {
//				log.info(note.toString());
//			}
//
//			// fetch an  by ID
//			Note note = noteDao.findOne(1);
//			log.info("found with findOne(1) {}", note);
//
//		};
//	}
	 
}

