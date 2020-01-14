package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.response.LoginResponse;
import com.bridgelabz.fundoonotes.service.LoginService;

@RestController
public class LoginController {

	@Autowired
	LoginService loginService;
	
	@Autowired
	LoginResponse loginResponse;
	
	@PostMapping("/login")
	public String login(@RequestBody LoginDTO logindto)
	{
		if(loginService.checkUser(logindto))
		return loginResponse.getToken(loginService.logger(logindto));
		else
		return "User Does Not Exist!!";
			
	}
}
