package com.bridgelabz.fundoonotes.serviceimpl;

import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.dto.ForgotDTO;
import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utility.Utility;

@Service
public class UserServiceImpl implements UserService,UserDetailsService{

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private BCryptPasswordEncoder bcrypt;
	

	
	private String jwt; 
	
	@Autowired
	private Utility utility;
	
	@Override
	public boolean Register(UserDTO userdto) {
		
		try {
			
			UserInfo user=modelMapper.map(userdto,UserInfo.class);
			userRepository.SaveUser(user.getId(),user.getUsername(),user.getFirstname(),user.getLastname(),user.getEmail(),user.getPassword());
			MailDetails(user.getEmail());
			return true;
		} 
		catch(Exception e) {
			
			e.printStackTrace();
			return false;
		}
		
	}
	
	
	public void MailDetails(String email)
	{
		UserInfo user=userRepository.findByEmail(email);
		String jwt=utility.generateToken(new User(user.getUsername(),user.getPassword(),new ArrayList<>()));
		String url="http://localhost:8080/checkemail?jwt="+jwt;
		utility.sendEMail(email,"verifying email",url);
	}
	
    public void PassDetails(String email) 
    {
    	UserInfo user=userRepository.findByEmail(email);
		String jwt=utility.generateToken(new User(user.getUsername(),user.getPassword(),new ArrayList<>()));
		String url="http://localhost:8080/checkemail?jwt="+jwt;
		utility.sendEMail(email,"changing password",url);
    }
	

	

	@Override
	public ResponseEntity<Response> login(LoginDTO logindto) throws LoginException {
		
		UserInfo user=userRepository.findByUsername(logindto.getUsername());
		if(!utility.checkUser(logindto.getUsername()))
		{
			throw new LoginException("Register Before Login!");
		}
		if(utility.checkVerified(logindto.getUsername()))
		{
			if(logindto.getUsername().equals(user.getUsername()) && bcrypt.matches(logindto.getPassword(),user.getPassword()))
			{
				String token=utility.generateToken(new User(logindto.getUsername(),logindto.getPassword(),new ArrayList<>()));
				return  ResponseEntity.ok().body(new Response(200,"Token Received",token));
			}
			else
				
			throw new LoginException("Bad Credentials");
		}
		else
		{
		 throw new LoginException("You are not a Verified User!");
		}
	}

	
	@Override
	public ResponseEntity<Response> verifyEmail(String jwt) throws JWTTokenException {
		if(utility.validateToken(jwt))
		{
			userRepository.setVerifiedEmail(utility.getUsernameFromToken(jwt));
			return null;
		}
	    throw new JWTTokenException("Not a Valid Token!");
	}
	

	@Override
	public String resetPassword(String password, String jwt) throws JWTTokenException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void forgotPassword(ForgotDTO forgotdto) {
		// TODO Auto-generated method stub
	try {
			
			UserInfo user=modelMapper.map(forgotdto,UserInfo.class);
			userRepository.SaveUser(user.getId(),user.getUsername(),user.getFirstname(),user.getLastname(),user.getEmail(),user.getPassword());
			PassDetails(user.getEmail());
			
		} 
		catch(Exception e) {
			
			e.printStackTrace();
		
		}
		
	}

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	UserInfo user=userRepository.findByUsername(username);
	return new User(user.getUsername(),user.getPassword(),new ArrayList());
		
	}

}
