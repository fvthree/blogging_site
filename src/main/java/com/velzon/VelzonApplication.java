package com.velzon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VelzonApplication {

	private static final Logger logger = LoggerFactory.getLogger(VelzonApplication.class);

	public static void main(String[] args) {
		logger.info("========================================");
		logger.info("Starting Velzon Blog Application...");
		logger.info("========================================");
		SpringApplication.run(VelzonApplication.class, args);
		logger.info("✅ Velzon Blog Application started successfully!");
	}

}