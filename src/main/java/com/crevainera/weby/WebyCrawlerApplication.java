package com.crevainera.weby;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WebyCrawlerApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebyCrawlerApplication.class, args);
	}

}
