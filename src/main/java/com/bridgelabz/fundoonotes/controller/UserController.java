package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.Exceptions.MailIDNotFoundException;
import com.bridgelabz.fundoonotes.dto.ForgotDTO;
import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.dto.PasswordDTO;
import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utility.Utility;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	Utility utility;
	
	
	@PostMapping("/register")
	public ResponseEntity<Response> registration(@Valid @RequestBody UserDTO userdto,BindingResult bindingresult)
	{
		
		System.out.println("Entered reg");
		if(bindingresult.hasErrors() || !userdto.getPassword().equals(userdto.getPasswordagain()))
		{
			return ResponseEntity.badRequest().body(new Response(400,"Errors_found",utility.getErrors(bindingresult,userdto)));
		}
		else
		
		if(userService.Register(userdto))
		return ResponseEntity.ok().body(new Response(200,"Registered Successfully",userdto));
		else
		return ResponseEntity.badRequest().body(new Response(400,"User already registered",userdto));
			
		
	}
	
	
	@GetMapping("/verifyemail/{jwt}")
	public ResponseEntity<Response> checkEmail(@PathVariable("jwt") String jwt) throws JWTTokenException
	{
		userService.verifyEmail(jwt);
		return ResponseEntity.ok().body(new Response(400,"Email Verified",null));
	}
	
	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody LoginDTO logindto) throws LoginException
	{	
		return userService.login(logindto);
		
	}
	@PostMapping("/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestBody ForgotDTO forgotdto) throws MailIDNotFoundException
	{
		if(utility.checkMail(forgotdto.getEmail()))
		{
			userService.forgotPassword(forgotdto);
			return ResponseEntity.ok().body(new Response(400,"Mail sent to your id",forgotdto));
		}
		else
			throw new MailIDNotFoundException("Not a valid mail id");
	}
	
	@PutMapping("/resetpassword/{jwt}")
	public ResponseEntity<Response> newPassword(@RequestBody PasswordDTO password,@PathVariable("jwt") String jwt) throws JWTTokenException
	{
		if(password.getNewpassword().equals(password.getConfirmnewpassword()))
		{
			String resetresult=userService.resetPassword(password.getNewpassword(),jwt);
			if(resetresult==null)
			return ResponseEntity.ok().body(new Response(200,"password redefined",null));
			else
			return ResponseEntity.badRequest().body(new Response(400,"Token Expired",null));
		}
		else
			return ResponseEntity.badRequest().body(new Response(400,"Password not matching",null));
		
	}
	

	@GetMapping("/displaytrash/{jwt}")
	public ResponseEntity<Response> displayTrashedNotesForUser(@PathVariable("jwt") String jwt) throws JWTTokenException
	{
		List<Notes> list=userService.displayTrashNotesByUser(jwt);
		
		if(list!=null)
		{
			return ResponseEntity.ok().body(new Response(200,"Trash Notes Displayed ",list));
		}
		else
		{
			return ResponseEntity.badRequest().body(new Response(400,"problem in Displaying Trash notes",list));
		}
		
	}
	
	@PutMapping("/restore/{jwt}")
	public ResponseEntity<Response> restoreFromTrashForUser(@PathVariable("jwt") String jwt,@RequestHeader("id") int id) throws JWTTokenException
	{
		boolean b=userService.restoreNoteFromTrash(jwt,id);
		if(b==true)
		{
			return ResponseEntity.ok().body(new Response(200,"Notes Restored ",id));
		}
		else
		{
			return ResponseEntity.badRequest().body(new Response(400,"problem in Restoring Trash notes",id));
		}
		
	}
	
	
	@PutMapping("/deleteforever/{jwt}")
	public ResponseEntity<Response> deleteFromTrashForUser(@PathVariable("jwt") String jwt,@RequestHeader("id") int id) throws JWTTokenException
	{
		boolean b=userService.deleteNoteFromTrash(jwt,id);
		if(b==true)
		{
			return ResponseEntity.ok().body(new Response(200,"Note Deleted from trash",id));
		}
		else
		{
			return ResponseEntity.badRequest().body(new Response(400,"problem in Deleting Trash notes",id));
		}
		
	}
	
	@PutMapping("/emptytrash/{jwt}")
	public ResponseEntity<Response> emptyTrashForUser(@PathVariable("jwt") String jwt,@RequestHeader("id") int id) throws JWTTokenException
	{
		boolean b=userService.emptyTrashByUser(jwt,id);
		if(b==true)
		{
			return ResponseEntity.ok().body(new Response(200,"Note Deleted from trash",id));
		}
		else
		{
			return ResponseEntity.badRequest().body(new Response(400,"problem in Deleting Trash notes",id));
		}
		
	}
	
    
	@GetMapping("/sortbyname/{jwt}")
	public ResponseEntity<Response> displaySortedNotesByName(@PathVariable("jwt") String jwt) throws JWTTokenException
	{
		Object[] notes=userService.displaySortedByName(jwt);
		
		if(notes!=null)
		{
			return ResponseEntity.ok().body(new Response(200,"Sorted Notes By Name Displayed ",notes));
		}
		else
		{
			return ResponseEntity.badRequest().body(new Response(400,"problem in Displaying Sorted notes",notes));
		}
		
	}

	 
		@GetMapping("/sortbyid/{jwt}")
		public ResponseEntity<Response> displaySortedNotesById(@PathVariable("jwt") String jwt) throws JWTTokenException
		{
			Object[] notes=userService.displaySortedById(jwt);
			
			if(notes!=null)
			{
				return ResponseEntity.ok().body(new Response(200,"Sorted Notes By Id Displayed ",notes));
			}
			else
			{
				return ResponseEntity.badRequest().body(new Response(400,"problem in Displaying Sorted notes",notes));
			}
			
		}
	
		
		
		@GetMapping("/sortbydate/{jwt}")
		public ResponseEntity<Response> displaySortedNotesByDate(@PathVariable("jwt") String jwt) throws JWTTokenException
		{
			Object[] notes=userService.displaySortedByDate(jwt);
			
			if(notes!=null)
			{
				return ResponseEntity.ok().body(new Response(200,"Sorted Notes By Date Displayed ",notes));
			}
			else
			{
				return ResponseEntity.badRequest().body(new Response(400,"problem in Displaying Sorted notes",notes));
			}
			
		}
}
