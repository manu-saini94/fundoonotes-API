package com.bridgelabz.fundoonotes.serviceimpl;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.LoginException;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.dto.ForgotDTO;
import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utility.Utility;

@Service
public class UserServiceImpl implements UserService,UserDetailsService{

	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	private BCryptPasswordEncoder bcrypt; 
	
	
	private String jwt; 
	
	@Autowired
	private Utility utility;
	
	@Override
	public boolean Register(UserDTO userdto) {
		
		boolean flag=false;
		try {
			
			UserInfo user=modelMapper.map(userdto,UserInfo.class);
			Integer I=userRepository.SaveUser(user.getUsername(),user.getFirstname(),user.getLastname(),user.getEmail(),user.getPassword());
			flag=true;
			MailDetails(user.getEmail());
				
		} 
		catch(Exception e) {
			
			e.printStackTrace();
			
		}
		return flag;
	}
	
	
	public void MailDetails(String email)
	{
		UserInfo user=userRepository.findByEmail(email);
		String jwt=utility.generateToken(new User(user.getUsername(),user.getPassword(),new ArrayList<>()));
		String url="http://localhost:8080/user/verifyemail?jwt="+jwt;
		utility.sendEMail(email,"verifying email",url);
	}
	
	
    public void PassDetails(String email) 
    {
    	UserInfo user=userRepository.findByEmail(email);
		String jwt=utility.generateToken(new User(user.getUsername(),user.getPassword(),new ArrayList<>()));
		String url="http://localhost:8080/user/resetpassword?jwt="+jwt;
		utility.sendEMail(email,"changing password",url);
    
    }
	

	

	@Override
	public ResponseEntity<Response> login(LoginDTO logindto) throws LoginException {
		
		UserInfo user=userRepository.findByUsername(logindto.getUsername());
		if(user==null)
		{
			throw new LoginException("Register Before Login!");
		}
		boolean x=utility.checkVerified(logindto.getUsername());
		System.out.println(x);
		if(x)
		{
			boolean a=logindto.getUsername().equals(user.getUsername());
			System.out.println(a);
			boolean b=logindto.getPassword().equals(user.getPassword());
			System.out.println(b);
			if(a && b)
			{
				String token=utility.generateToken(new User(logindto.getUsername(),logindto.getPassword(),new ArrayList<>()));
				return  ResponseEntity.ok().body(new Response(200,"Token Received",token));
			}
			else
				
			throw new LoginException("Bad Credentials");
		}
		else
		{
		 throw new LoginException("You are not a Verified User!");
		}
	}

	
	@Override
	public ResponseEntity<Response> verifyEmail(String jwt) throws JWTTokenException {
		if(utility.validateToken(jwt))
		{
			userRepository.setVerifiedEmail(utility.getUsernameFromToken(jwt));
			return null;
		}
	    throw new JWTTokenException("Not a Valid Token!");
	}
	

	@Override
	public String resetPassword(String password, String jwt) throws JWTTokenException {
		if(utility.validateToken(jwt))
		{
		userRepository.changepassword(password,utility.getUsernameFromToken(jwt));
        return null;
		}
		else
		{
			throw new JWTTokenException("Not a valid token");
		}
	}

	
	@Override
	public void forgotPassword(ForgotDTO forgotdto) {
		
	try {
			
			UserInfo user=modelMapper.map(forgotdto,UserInfo.class);
			PassDetails(user.getEmail());
			
		} 
		catch(Exception e) {
			
			e.printStackTrace();
		
		}
		
	}

	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	UserInfo user=userRepository.findByUsername(username);
	return new User(user.getUsername(),user.getPassword(),new ArrayList<>());
		
	}

	
	@Override
	public List<Notes> displayTrashNotesByUser(String jwt) throws JWTTokenException {
		UserInfo user=null;
		if(utility.validateToken(jwt))
		{
		user=utility.getUser(jwt);
		List<Notes> notes=userRepository.getTrashedNotesByUser(user.getId());
		return notes;
		}
		else
		throw new JWTTokenException("Token Not Found Exception");	
		
	}


	@Override
	public boolean restoreNoteFromTrash(String jwt, int id) throws JWTTokenException {
   		
		UserInfo user=null;
		if(utility.validateToken(jwt))
		{
		user=utility.getUser(jwt);
		Notes note=userRepository.findNoteById(user,id);
		int i=userRepository.restoreNoteByUser(user.getId(),note.getId());
        if(i!=0)
	    return true;
        else
	    return false;
			}
		else
		throw new JWTTokenException("Token Not Found Exception");		
		}


	@Override
	public boolean deleteNoteFromTrash(String jwt, int id) throws JWTTokenException {
	
		UserInfo user=null;
		if(utility.validateToken(jwt))
		{
		user=utility.getUser(jwt);
		Notes note=userRepository.findNoteById(user,id);
		int i=userRepository.deleteNoteFromTrashByUser(user.getId(),note.getId());
        if(i!=0)
	    return true;
        else
	    return false;
			}
		else
		throw new JWTTokenException("Token Not Found Exception");		
	
	}


	@Override
	public boolean emptyTrashByUser(String jwt, int id) throws JWTTokenException 
	{
		UserInfo user=null;
		if(utility.validateToken(jwt))
		{
		user=utility.getUser(jwt);
		Notes note=userRepository.findNoteById(user,id);
		int i=userRepository.emptyTrashForUser(user.getId(),note.getId());
        if(i!=0)
	    return true;
        else
	    return false;
			}
		else
		throw new JWTTokenException("Token Not Found Exception");	
		
		}


	@Override
	public  Object[] displaySortedByName(String jwt) throws JWTTokenException {
		UserInfo user=null;
		if(utility.validateToken(jwt))
		{
		user=utility.getUser(jwt);
		Object[] notes=userRepository.getSortedNotesByName(user.getId());
		System.out.println(notes);
		return notes;
		}
		else
		throw new JWTTokenException("Token Not Found Exception");	

	}


	@Override
	public Object[] displaySortedById(String jwt) throws JWTTokenException {
		UserInfo user=null;
		if(utility.validateToken(jwt))
		{
		user=utility.getUser(jwt);
		Object[] notes=userRepository.getSortedNotesById(user.getId());
		System.out.println(notes);
		return notes;
		}
		else
		throw new JWTTokenException("Token Not Found Exception");		}


	@Override
	public Object[] displaySortedByDate(String jwt) throws JWTTokenException {
		
		UserInfo user=null;
		if(utility.validateToken(jwt))
		{
		user=utility.getUser(jwt);
		Object[] notes=userRepository.getSortedNotesByDate(user.getId());
		System.out.println(notes);
		return notes;
		}
		else
		throw new JWTTokenException("Token Not Found Exception");	}
}
