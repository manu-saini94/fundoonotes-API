package com.bridgelabz.fundoonotes.serviceimplementation;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.ForgotService;
import com.bridgelabz.fundoonotes.utility.Utility;

@Service
public class ForgotPassImplementation implements ForgotService
{

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	private UserRepository repository;
	@Autowired
	private Utility utility=new Utility();
	
	public String jwt;
	
	public void send(String email_id)
	{
	    kafkaTemplate.send("Email_send",email_id);
	}
	
	
	@KafkaListener(topics = "Email_send", groupId = "group-id")
	public void consume(String message)
	{
		sendMail(message);
		kafkaTemplate.flush();
	}
	
	public void sendMail(String email)
	{
		UserInfo user=repository.findByEmail(email);
		String jwt=utility.generateToken(new User(user.getUsername(),user.getPassword(),new ArrayList<>()));
		String url="http://localhost:8082/changepass?jwt="+jwt;
		this.jwt=jwt;
		utility.sendMail(email,"changing password",url);
	}
	
	public void changepassword(String password,String username)
	{
		repository.changepassword(password,username);
	}
	
	public boolean checkJWT(String token)
	{
		return token.equals(jwt)&&utility.validateToken(token);
	}
	public boolean checkmail(String email)
	{
		return repository.findByEmail(email)!=null;
	}



}

