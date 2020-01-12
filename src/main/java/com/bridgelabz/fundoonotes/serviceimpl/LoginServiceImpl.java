package com.bridgelabz.fundoonotes.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.repository.LoginRepository;
import com.bridgelabz.fundoonotes.service.LoginService;

public class LoginServiceImpl implements LoginService{

	@Autowired
	LoginRepository loginRepository;
	
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

}
