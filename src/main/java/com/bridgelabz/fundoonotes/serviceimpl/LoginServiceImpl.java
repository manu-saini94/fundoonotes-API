package com.bridgelabz.fundoonotes.serviceimpl;

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
import com.bridgelabz.fundoonotes.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService,UserDetailsService{

	@Autowired
	LoginRepository loginRepository;
	
	BCryptPasswordEncoder bcrypt;
	
	LoginDTO logindto;
	
	@Override
	public boolean checkUser(LoginDTO logindto) {
		// TODO Auto-generated method stub
		this.logindto=logindto;
		if(loginRepository.findByUsername(logindto.getUsername())!=null)
		return true;
		else 
		return false;
	}

	@Override
	public UserDetails logger(LoginDTO logindto) {
		// TODO Auto-generated method stub
		UserInfo user=loginRepository.findByUsername(logindto.getUsername());
		if(logindto.getUsername().equals(user.getUsername()) && bcrypt.matches(logindto.getPassword(),user.getPassword()))
		{
			return new User(logindto.getUsername(),logindto.getPassword(),new ArrayList());
		}
	    return null;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		UserInfo user=loginRepository.findByUsername(username);
		return new User(user.getUsername(),user.getPassword(),new ArrayList());
	}



}
