package com.todoapplication.app;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EntityScan(basePackageClasses = { 
		TodoApplication.class,
		Jsr310JpaConverters.class 
})
public class TodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}
	
	public WebMvcConfigurer corsConfigurer() { 
		   return new WebMvcConfigurer() {
		      @Override
		      public void addCorsMappings(CorsRegistry registry) {
		         registry.addMapping("/**").allowedHeaders("*").allowedOrigins("*");
		      } 
		   };
		}
	
	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

}
