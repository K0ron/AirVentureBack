package com.keca.AirVentureBack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication(scanBasePackages = "com.keca.AirVentureBack")
public class AirVentureBackApplication {
	private static final Logger logger = LoggerFactory.getLogger(AirVentureBackApplication.class);


	public static void main(String[] args) {

		logger.info("SCW_ACCESS_KEY: {}", System.getenv("SCW_ACCESS_KEY"));
        logger.info("SCW_SECRET_KEY: {}", System.getenv("SCW_SECRET_KEY"));

		SpringApplication.run(AirVentureBackApplication.class, args);
	}

}
