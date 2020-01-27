package com.bridgelabz.fundoonotes.service;

import java.util.List;

import javax.security.auth.login.LoginException;

import org.springframework.http.ResponseEntity;

import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.dto.ForgotDTO;
import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.response.Response;

public interface UserService {

	public boolean Register(UserDTO userdto);
	
	public ResponseEntity<Response> login(LoginDTO logindto) throws LoginException;
	
	public ResponseEntity<Response> verifyEmail(String jwt) throws JWTTokenException;
	
	public String resetPassword(String password,String jwt) throws JWTTokenException;
	
	public void forgotPassword(ForgotDTO forgotdto);
	
	List<Notes> displayTrashNotesByUser(String jwt) throws JWTTokenException;
	
	public boolean restoreNoteFromTrash(String jwt, int id) throws JWTTokenException;
	
	public boolean deleteNoteFromTrash(String jwt, int id) throws JWTTokenException;
	
	public boolean emptyTrashByUser(String jwt, int id) throws JWTTokenException;

	public Object[] displaySortedByName(String jwt) throws JWTTokenException;

	public Object[] displaySortedById(String jwt) throws JWTTokenException;

	public Object[] displaySortedByDate(String jwt) throws JWTTokenException;
	
	
	
	
	
	
	
	
	
	
	
	
}
