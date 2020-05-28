package com.hmo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication()
public class EdiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EdiApplication.class, args);
	}

}
