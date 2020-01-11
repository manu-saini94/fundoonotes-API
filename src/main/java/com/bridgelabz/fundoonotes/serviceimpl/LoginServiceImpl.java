package com.bridgelabz.fundoonotes.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.fundoonotes.service.LoginDTO;
import com.bridgelabz.fundoonotes.service.LoginService;

public class LoginServiceImpl implements LoginService{

	@Autowired
	LoginRepository loginrepository;
	
	LoginDto logindto;
	@Override
	public boolean checkUser(LoginDTO logindto) {
		this.logindto=logindto;
		if(repository.findByUsername(logindto.getUsername())!=null)
		return true;
		else 
		return false;
	}

}
