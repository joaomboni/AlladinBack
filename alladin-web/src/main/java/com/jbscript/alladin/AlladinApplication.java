package com.jbscript.alladin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = "com.jbscript.alladin")
public class AlladinApplication {

	public static void main(String[] args) {

		SpringApplication.run(AlladinApplication.class, args);
	}

}
