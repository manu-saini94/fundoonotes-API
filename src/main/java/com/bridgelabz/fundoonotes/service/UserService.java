package com.bridgelabz.fundoonotes.service;

import java.util.List;

import javax.security.auth.login.LoginException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.dto.ForgotDTO;
import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.response.Response;

public interface UserService {

	public boolean Register(UserDTO userdto);
	
	public String login(LoginDTO logindto) throws LoginException;
	
	public boolean verifyEmail(String jwt) throws JWTTokenException;
	
	public String resetPassword(String password,String jwt) throws JWTTokenException;
	
	public void forgotPassword(ForgotDTO forgotdto);

	UserDetails loadUserByEmail(String email) throws UsernameNotFoundException;
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
