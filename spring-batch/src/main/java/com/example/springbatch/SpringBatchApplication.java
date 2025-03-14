package com.example.springbatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SpringBatchApplication {


	public static void main(String[] args) {
		SpringApplication.run(SpringBatchApplication.class, args);
	}

}
