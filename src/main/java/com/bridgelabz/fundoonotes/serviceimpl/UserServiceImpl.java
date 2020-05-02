package com.bridgelabz.fundoonotes.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
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
import com.bridgelabz.fundoonotes.model.Notes;
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
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	private String jwt; 
	
	@Autowired
	private Utility utility;
	
	@Override
	public boolean Register(UserDTO userdto) {
		Integer I=0;
		String token=null;
		try {		
			UserInfo user=modelMapper.map(userdto,UserInfo.class);
			I=userRepository.SaveUser(user.getMobileno(),user.getFirstname(),user.getLastname(),user.getEmail(),user.getPassword());	
		    kafkaTemplate.send("myfundoo",user.getEmail());				
		} 
		catch(Exception e) {		
			e.printStackTrace();		
		}
		if(I!=0)
		return true;
		else
		return false;
	}
	
	@KafkaListener(topics = "myfundoo", groupId = "group")
	 public void consume(String message) {
	 UserInfo user=userRepository.findByEmail(message);
	 String token=utility.generateToken(new User(user.getEmail(),user.getPassword(),new ArrayList<>()));
     String url="http://localhost:3000/verify/"+token;
	 utility.sendEMail(message,"verifying email",url);
	  kafkaTemplate.flush();  
	  }
	 
	@KafkaListener(topics = "myfundoo2", groupId = "group")
	 public void consume2(String message) {
		UserInfo user=userRepository.findByEmail(message);
		String jwt=utility.generateToken(new User(user.getEmail(),user.getPassword(),new ArrayList<>()));
		String url="http://localhost:3000/reset/"+jwt;
		utility.sendEMail(message,"changing password",url);
	  kafkaTemplate.flush();  
	  }
	

	@Override
	public String login(LoginDTO logindto) throws LoginException {
		
		UserInfo user=userRepository.findByEmail(logindto.getEmail());
		if(user==null)
		{
			throw new LoginException("Register Before Login!");
		}
		boolean x=utility.checkVerified(logindto.getEmail());
		if(x)
		{
			boolean a=logindto.getEmail().equals(user.getEmail());
			boolean b=logindto.getPassword().equals(user.getPassword());
			if(a && b)
			{
				String token=utility.generateToken(new User(logindto.getEmail(),logindto.getPassword(),new ArrayList<>()));
				return  token;
			}
			else				
			throw new LoginException("Bad Credentials");
		}
		else
		 throw new LoginException("You are not a Verified User!");
		
	}

	
	@Override
	public boolean verifyEmail(String jwt) throws JWTTokenException {
		if(utility.validateToken(jwt))
		{
			Integer I=userRepository.setVerifiedEmail(utility.getUsernameFromToken(jwt));
			if(I!=0)
				return true;
			else
				return false;
		}
	    throw new JWTTokenException("Not a Valid Token!");
	}
	

	@Override
	public String resetPassword(String password, String jwt) throws JWTTokenException {
		if(utility.validateToken(jwt))
		{
		userRepository.changepassword(password,utility.getUsernameFromToken(jwt));
        return null;
		}
		else
		{
			throw new JWTTokenException("Not a valid token");
		}
	}

	
	@Override
	public void forgotPassword(ForgotDTO forgotdto) {		
	try {		
			UserInfo user=modelMapper.map(forgotdto,UserInfo.class);
		    kafkaTemplate.send("myfundoo2",user.getEmail());							
		} 
		catch(Exception e) {			
			e.printStackTrace();
		
		}	
	}

	
	@Override
	public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
	UserInfo user=userRepository.findByEmail(email);
	return new User(user.getEmail(),user.getPassword(),new ArrayList<>());		
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserInfo user=userRepository.findByEmail(username); 
		return new User(user.getEmail(),user.getPassword(),new ArrayList<>());
	}


	

	
	
}
