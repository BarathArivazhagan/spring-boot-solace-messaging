package com.barath.jms.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.core.JmsTemplate;

@SpringBootApplication
public class Application {


	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
