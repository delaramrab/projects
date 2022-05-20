package com.mapsa.dotin.service;

import com.mapsa.dotin.dto.UserRegistrationDto;
import com.mapsa.dotin.model.person.Person;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
	Person save(UserRegistrationDto registrationDto);
}
