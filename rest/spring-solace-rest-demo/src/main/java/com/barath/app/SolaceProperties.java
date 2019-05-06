package com.barath.app;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(ignoreUnknownFields=true,prefix="solace.rest")
public class SolaceProperties {
		
	
	private String url;
	
	private String username;
	
	private String password;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SolaceProperties(String url, String username, String password) {
		super();
		this.url = url;
		this.username = username;
		this.password = password;
	}

	public SolaceProperties() {
		super();
		
	}

	@Override
	public String toString() {
		return "SolaceProperties [url=" + url + ", username=" + username + ", password=" + password + "]";
	}
	
	
	
}
