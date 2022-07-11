package com.example.CRUDApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class CrudAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudAppApplication.class, args);
	}

}
