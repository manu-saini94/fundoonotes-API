package com.bridgelabz.fundoonotes.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.response.UserResponse;
import com.bridgelabz.fundoonotes.service.UserService;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/users")
	public Map userCreation(@Valid @RequestBody UserDTO userdto,BindingResult bindingresult)
	{
		List<String> errors=new ArrayList<String>();
		for(FieldError error:bindingresult.getFieldErrors())
		{
			errors.add(error.getField()+" : "+error.getDefaultMessage());			
		}
		if(!userdto.getPassword().equals(userdto.getPasswordagain()))
		{
			errors.add("re-entered password is not matching");
		}
    	if(userService.checkUser(userdto.getUsername()))
		errors.add("username already exist");
    	
    	if(errors.size()<1)
    	{
    		userService.createUser(userdto);
    		System.out.println("isnide controller");
    		return new UserResponse().ok("successfully created",200,errors);
    	}
    		else
    		{
    		return new UserResponse().badRequest("bad request",400,errors);
    	}
	}
	
	@PostMapping("/checkemail")
	public String checkEmail(@RequestParam("jwt") String jwt)
	{
		
	  userService.checkJWT(jwt);
      return "Email Verified";
	}
	
}
