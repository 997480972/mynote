package com;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;


@SpringBootApplication(exclude={DataSourceAutoConfiguration.class,HibernateJpaAutoConfiguration.class})//无数据库运行
@EnableDiscoveryClient
@EnableFeignClients
@Slf4j
public class WebApp{
	 public static void main(String[] args) {
		 System.out.println(" springApplication run !");
		 SpringApplication.run(WebApp.class,args);
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

