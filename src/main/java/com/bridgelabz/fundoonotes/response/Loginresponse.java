package com.bridgelabz.fundoonotes.response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.bridgelabz.fundoonotes.utility.Utility;


public class Loginresponse 
{
	
	Utility utility=new Utility();
	
	
	public String getToken(UserDetails userdetails)
	{
	  	return utility.generateToken(userdetails);
	}

}
