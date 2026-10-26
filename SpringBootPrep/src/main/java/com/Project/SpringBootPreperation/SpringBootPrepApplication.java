package com.Project.SpringBootPreperation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringBootPrepApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootPrepApplication.class, args);
	}

}
