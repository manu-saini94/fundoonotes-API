package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.UserDTO;

public interface UserService {

	boolean createUser(UserDTO userdto);
	boolean checkUser(String username);
	public void checkJWT(String jwt);

}
