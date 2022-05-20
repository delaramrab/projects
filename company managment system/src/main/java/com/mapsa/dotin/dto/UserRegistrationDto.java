package com.mapsa.dotin.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
public class UserRegistrationDto {
	private String firstName;
	private String lastName;
	private String emailAddress;
	public String username;
	private String password;
	
	public UserRegistrationDto(){
		
	}
	
	public UserRegistrationDto(String firstName, String lastName,String username, String emailAddress, String password) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.username=username;
		this.emailAddress = emailAddress;
		this.password = password;
	}
	

}
