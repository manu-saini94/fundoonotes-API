package com.bridgelabz.fundoonotes.serviceimplementation;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utility.Utility;


@Service
public class UserImplementation implements UserService {
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	UserRepository repository;
	@Autowired
	ModelMapper mapper;
	@Autowired
	BCryptPasswordEncoder bcrypt;
	@Autowired
	private JavaMailSender javaMailSender;
	
	Utility utility=new Utility();
	
	public boolean checkUser(String username)
	{
		return repository.findByUsername(username)!=null;
	}
   
	
	public boolean createUser(UserDTO userDto)
	  {
		  try 
		  {  
			    UserInfo user=mapper.map(userDto,UserInfo.class);
	        	repository.SaveUser(user.getId(), user.getUsername(),user.getFirstname(),user.getLastname(),user.getEmail(), bcrypt.encode(user.getPassword()));
	        	 kafkaTemplate.send("verification",user.getEmail());
	        	return true;
		  }
		  catch(Exception e)
		  {
			  e.printStackTrace();
			  return false;
		  }		 
	  }
	
	@KafkaListener(topics = "verification", groupId = "group-id")
	public void consume(String message)
	{
		MailDetails(message);
	    kafkaTemplate.flush();
	
	}
	
	
	public void MailDetails(String email)
	{
		UserInfo user=repository.findByEmail(email);
		System.out.println(user);
		String jwt=utility.generateToken(new User(user.getUsername(),user.getPassword(),new ArrayList<>()));
		String url="http://localhost:8082/checkemail?jwt="+jwt;
		sendEMail(email,"verifying email",url);
	}
	
	
	public void sendEMail(String toEmail, String subject, String message) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setTo(toEmail);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);
		mailMessage.setFrom("nandhukavi100@gmail.com");
		System.out.println(mailMessage);
		javaMailSender.send(mailMessage);
	}
	
	public void checkJWT(String jwt)
	{
		if(utility.validateToken(jwt))
		{
			repository.setVerifiedEmail(utility.getUsernameFromToken(jwt));
		}
	}

}
