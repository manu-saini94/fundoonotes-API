package com.bridgelabz.fundoonotes.serviceimplementation;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.repository.LoginRepository;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.LoginService;

import net.bytebuddy.asm.Advice.Return;


@Service
public class LoginImplementation implements LoginService,UserDetailsService
{
	@Autowired
	LoginRepository repository;
	
	@Autowired
	BCryptPasswordEncoder bcrypt;
	
	LoginDTO logindto;
	
	public boolean checkUser(LoginDTO logindto)
	{
		this.logindto=logindto;
		if(repository.findByUsername(logindto.getUsername())!=null)
			return true;
			else
			return false;
	}
	
	public UserDetails logger(LoginDTO logindto)
	{
		UserInfo user=repository.findByUsername(logindto.getUsername());
		
		if(logindto.getUsername().equals(user.getUsername()) && bcrypt.matches(logindto.getPassword(),user.getPassword()))
		{
			return new User(logindto.getUsername(),logindto.getPassword(),new ArrayList());
		}
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
	
		UserInfo user=repository.findByUsername(username);
		return new User(user.getUsername(),user.getPassword(),new ArrayList());
	}
	
}
