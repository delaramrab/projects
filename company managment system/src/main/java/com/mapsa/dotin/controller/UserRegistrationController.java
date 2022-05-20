package com.mapsa.dotin.controller;


import com.mapsa.dotin.dto.UserRegistrationDto;
import com.mapsa.dotin.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {

	private UserService userService;

	public UserRegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@ModelAttribute("Person")
    public UserRegistrationDto userRegistrationDto() {

		return new UserRegistrationDto();
    }
	
	@GetMapping
	public String showRegistrationForm() {

		return "registration";
	}
	
	@PostMapping
	public String registerUserAccount(@ModelAttribute("Person") UserRegistrationDto registrationDto) {
		userService.save(registrationDto);
		return "redirect:/registration?success";
	}
}
