package com.bridgelabz.fundoonotes.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.Exceptions.MailIDNotFoundException;
import com.bridgelabz.fundoonotes.dto.ForgotDTO;
import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.dto.PasswordDTO;
import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.UserResponse;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utility.Utility;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	Utility utility;
	
	
	@PostMapping("/register")
	public ResponseEntity<Response> registration(@Valid @RequestBody UserDTO userdto,BindingResult bindingresult)
	{
		
		System.out.println("Entered reg");
		if(bindingresult.hasErrors() || !userdto.getPassword().equals(userdto.getPasswordagain()))
		{
			return ResponseEntity.badRequest().body(new Response(400,"Errors_found",utility.getErrors(bindingresult,userdto)));
		}
		else
		
		if(userService.Register(userdto))
		return ResponseEntity.ok().body(new Response(200,"Registered Successfully",userdto));
		else
		return ResponseEntity.badRequest().body(new Response(400,"User already registered",userdto));
			
		
	}
	
	@GetMapping("/verifyemail/{jwt}")
	public ResponseEntity<Response> checkEmail(@PathVariable("jwt") String jwt) throws JWTTokenException
	{
		userService.verifyEmail(jwt);
		return ResponseEntity.ok().body(new Response(400,"Email Verified",null));
	}
	
	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody LoginDTO logindto) throws LoginException
	{	
		return userService.login(logindto);
		
	}
	@PostMapping("/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestBody ForgotDTO forgotdto) throws MailIDNotFoundException
	{
		if(utility.checkMail(forgotdto.getEmail()))
		{
			userService.forgotPassword(forgotdto);
			return ResponseEntity.ok().body(new Response(400,"Mail sent to your id",forgotdto));
		}
		else
			throw new MailIDNotFoundException("Not a valid mail id");
	}
	
	@PutMapping("/resetpassword/{jwt}")
	public ResponseEntity<Response> newPassword(@RequestBody PasswordDTO password,@PathVariable("jwt") String jwt) throws JWTTokenException
	{
		if(password.getNewpassword().equals(password.getConfirmnewpassword()))
		{
			String resetresult=userService.resetPassword(password.getNewpassword(),jwt);
			if(resetresult==null)
			return ResponseEntity.ok().body(new Response(200,"password redefined",null));
			else
			return ResponseEntity.badRequest().body(new Response(400,"Token Expired",null));
		}
		else
			return ResponseEntity.badRequest().body(new Response(400,"Password not matching",null));
		
	}
}
