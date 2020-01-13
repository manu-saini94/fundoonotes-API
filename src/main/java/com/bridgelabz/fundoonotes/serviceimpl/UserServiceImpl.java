package com.bridgelabz.fundoonotes.serviceimpl;

import java.util.ArrayList;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utility.Utility;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private KafkaTemplate<String,String> kafkaTemplate;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private BCryptPasswordEncoder bcrypt;
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Utility utility;
	
	
	@Override
	public boolean checkUser(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username)!=null;
	}
	
	@Override
	public boolean createUser(UserDTO userdto) {
		// TODO Auto-generated method stub
	try
	{
		UserInfo user=modelMapper.map(userdto,UserInfo.class);
		userRepository.SaveUser(user.getId(),user.getUsername(),user.getFirstname(),user.getLastname(),user.getEmail(),bcrypt.encode(user.getPassword()));
	    kafkaTemplate.send("verification",user.getEmail());
	    return true;
	}
	catch(Exception e)
	{
	e.printStackTrace();
	return false;
	}
	}

	@KafkaListener(topics="verification",groupId="group-id")
	public void consume(String message)
	{
		MailDetails(message);
		kafkaTemplate.flush();
	}
	
	public void MailDetails(String email)
	{
		UserInfo user=userRepository.findByEmail(email);
		String jwt=utility.generateToken(new User(user.getUsername(),user.getPassword(),new ArrayList<>()));
		String url="http://localhost:8082/checkemail?jwt="+jwt;
		sendEMail(email,"verifying email",url);
	}
	
	
	public void sendEMail(String toEmail,String subject,String message)
	{
		SimpleMailMessage mail=new SimpleMailMessage();
		mail.setTo(toEmail);
		mail.setSubject(subject);
		mail.setText(message);
		mail.setFrom("manu.saini931222@gmail.com");
		System.out.println(mail);
		javaMailSender.send(mail);
	}
	
	

	@Override
	public void checkJWT(String jwt) {
		// TODO Auto-generated method stub
		if(utility.validateToken(jwt))
		{
			userRepository.setVerifiedEmail(utility.getUsernameFromToken(jwt));
		}
	}

}
