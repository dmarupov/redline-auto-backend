package com.capstone.redline;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@ComponentScan(basePackages={"controller", "repository" , "objects"})
public class RedlineApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedlineApplication.class, args);
	}
}
