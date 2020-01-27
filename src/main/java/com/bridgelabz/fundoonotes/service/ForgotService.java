package com.bridgelabz.fundoonotes.service;

public interface ForgotService 
{

	public void send(String email_id);
	public boolean checkmail(String email);
	public void sendMail(String email);
	public boolean checkJWT(String token);
	public void changepassword(String password,String email);

}
