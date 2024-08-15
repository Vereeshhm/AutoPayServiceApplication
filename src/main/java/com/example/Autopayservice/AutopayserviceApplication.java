package com.example.Autopayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication

@EntityScan(basePackages = {"com.example.Autopayservice.Apilogentity","com.example.Autopayservice.Utils","com.example.Autopayservice.Responses"})
public class AutopayserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AutopayserviceApplication.class, args);
	}

}
