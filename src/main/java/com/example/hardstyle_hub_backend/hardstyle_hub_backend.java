package com.example.hardstyle_hub_backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class hardstyle_hub_backend {

	public static void main(String[] args) {
		SpringApplication.run(hardstyle_hub_backend.class, args);
	}

}
