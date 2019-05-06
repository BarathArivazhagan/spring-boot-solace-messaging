package com.barath.app;

import java.lang.invoke.MethodHandles;
import java.util.Base64;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestProducerService {
	
	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	
	private final SolaceProperties solaceProperties;
	private final RestTemplate restTemplate;
	
	public RestProducerService(SolaceProperties solaceProperties) {
		this.restTemplate= new RestTemplate();
		this.solaceProperties= solaceProperties;
		
	}
	
	@PostConstruct
	public void send() {
		
		logger.info("sending data to solace rest with url {}",solaceProperties.getUrl());
		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Basic "+this.base64Encoded(this.solaceProperties.getUsername()+":"+this.solaceProperties.getPassword()));
		HttpEntity<Object> requestEntity= new HttpEntity<Object>(new User(1L, "barath"),headers);
		
		ResponseEntity<String> responseEntity= restTemplate.exchange(solaceProperties.getUrl(), HttpMethod.POST, requestEntity, String.class);
		if(responseEntity.getStatusCode().is2xxSuccessful()) {
			logger.info(" rest successful");
		}else {
			logger.error("error in connecting to rest ");
		}
	
	}
	
	private String base64Encoded(String input) {
		
		return Base64.getEncoder().encodeToString(input.getBytes());
	}
}
class User{
	
	private Long userId;
	
	private String userName;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public User(Long userId, String userName) {
		super();
		this.userId = userId;
		this.userName = userName;
	}

	public User() {
		super();
		
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + "]";
	}
	
	
}