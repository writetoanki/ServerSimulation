package com.ofbusiness.chatserver;

import org.apache.logging.log4j.LogManager;

import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ChatserverApplication {
	private static final Logger LOGGER= LogManager.getLogger(SpringApplication.class);
	public static void main(String[] args) {
		LOGGER.info("Starting the application Chat Server");
		SpringApplication.run(ChatserverApplication.class, args);
	}

}
