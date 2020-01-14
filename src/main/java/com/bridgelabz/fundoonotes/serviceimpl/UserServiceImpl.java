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
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private BCryptPasswordEncoder bcrypt;
	

	
	private String jwt; 
	
	@Autowired
	private Utility utility;
	
	
	@Override
	public boolean checkUser(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByUsername(username)!=null;
	}
	
	@Override
	public boolean createUser(UserDTO userdto) {
	try
	{
		System.out.println("iaufhci8ua7gtsf");
		UserInfo user=modelMapper.map(userdto,UserInfo.class);
		userRepository.save(user);
	    MailDetails(user.getEmail());
	    return true;
	}
	catch(Exception e)
	{
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
	

	@Override
	public void checkJWT(String token) {
		// TODO Auto-generated method stub
		if(token.equals(jwt) && utility.validateToken(jwt))
		{
			System.out.println("Verify");
			userRepository.setVerifiedEmail(utility.getUsernameFromToken(jwt));
		}
	}

}
