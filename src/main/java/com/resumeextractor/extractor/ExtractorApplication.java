package com.resumeextractor.extractor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class ExtractorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExtractorApplication.class, args);
	}

}
