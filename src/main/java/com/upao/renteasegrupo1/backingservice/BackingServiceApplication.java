package com.upao.renteasegrupo1.backingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.upao.renteasegrupo1.backingservice.repository")
public class BackingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackingServiceApplication.class, args);
	}

}
