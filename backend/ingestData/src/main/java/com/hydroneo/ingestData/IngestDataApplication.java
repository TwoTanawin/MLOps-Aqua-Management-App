package com.hydroneo.ingestData;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class IngestDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(IngestDataApplication.class, args);
	}

}
