package com.itg.exam;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;

@EnableConfigurationProperties
@SpringBootConfiguration
@EnableAutoConfiguration
@ComponentScan(basePackages = "com.itg.exam.*, io.swagger")
public class CodingExamApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodingExamApplication.class, args);
	}

}
