package com.bridgelabz.fundoonotes.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.bridgelabz.fundoonotes.dto.ForgotDTO;
import com.bridgelabz.fundoonotes.dto.PasswordDTO;
import com.bridgelabz.fundoonotes.service.ForgotService;
import com.bridgelabz.fundoonotes.utility.Utility;

@RestController
public class ForgotPassController 
{
	@Autowired
	ForgotService forgotservice;
	
	Utility util=new Utility();
	
	private String email;
	
	@PostMapping("forgotpass")
	public String forgotPassword(@RequestBody ForgotDTO forgotdto)
	{
		if(forgotservice.checkmail(forgotdto.getEmail()))
		{
		this.email=forgotdto.getEmail();
		forgotservice.send(forgotdto.getEmail());
		return "inside send";
		}
		else
		{
			return "Email Id not found";
		}
	}
	
	@RequestMapping("changepass")
	public String changePassword(@RequestParam("jwt") String jwt)
	{
	  if(forgotservice.checkJWT(jwt))
	  {
		return jwt;
	  }
		
		return "no authorized jwt";
	}
	
	@RequestMapping("newpass")
	public String newPassword(@RequestBody PasswordDTO password,HttpServletRequest request)
	{
	
		if(password.getNewpassword().equals(password.getConfirmnewpassword()))
		{
			String username=util.getUsernameFromToken(request.getHeader("header"));
			System.out.println(username);
			forgotservice.changepassword(password.getNewpassword(),username);
			return "changed successfully";
		}
		else
		{
			return "password not matching";
		}
		
	}
	
}
