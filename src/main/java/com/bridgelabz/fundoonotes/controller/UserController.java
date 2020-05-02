package com.bridgelabz.fundoonotes.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.security.auth.login.LoginException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.Exceptions.MailIDNotFoundException;
import com.bridgelabz.fundoonotes.Exceptions.S3BucketException;
import com.bridgelabz.fundoonotes.dto.ForgotDTO;
import com.bridgelabz.fundoonotes.dto.LoginDTO;
import com.bridgelabz.fundoonotes.dto.PasswordDTO;
import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.response.S3Response;
import com.bridgelabz.fundoonotes.service.AmazonS3ClientService;
import com.bridgelabz.fundoonotes.service.UserService;
import com.bridgelabz.fundoonotes.utility.Utility;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	Utility utility;
	
	@Autowired
	private AmazonS3ClientService amazonS3ClientService;
	
	@PostMapping("/register")
	public ResponseEntity<Response> registration(@Valid @RequestBody UserDTO userdto,BindingResult bindingresult)
	{
		
		System.out.println("Entered reg");
		if(bindingresult.hasErrors() || !userdto.getPassword().equals(userdto.getPasswordagain()))
		{
			return ResponseEntity.badRequest().body(new Response(400,"Errors_found",utility.getErrors(bindingresult,userdto)));
		}
		else
		{
		boolean b=userService.Register(userdto);
		if(b)
			return ResponseEntity.ok().body(new Response(200,"Registered Successfully",b));
		else
		    return ResponseEntity.badRequest().body(new Response(400,"User already registered",b));
		}	
		
	}
	
	
	@PutMapping("/verifyemail/{jwt}")
	public ResponseEntity<Response> checkEmail(@PathVariable("jwt") String jwt) throws JWTTokenException
	{
		System.out.println("welome.....");
		boolean b=userService.verifyEmail(jwt);
	
		if(b)
			return ResponseEntity.ok().body(new Response(200,"Email Verified",b));
		else
		    return ResponseEntity.badRequest().body(new Response(400,"problem in verification",b));
		
	}
	
	@PostMapping("/login")
	public ResponseEntity<Response> login(@RequestBody LoginDTO logindto) throws LoginException, MailIDNotFoundException
	{	
		if(utility.checkMail(logindto.getEmail()))
		{
			String token=userService.login(logindto);
			if(token!=null)
			return ResponseEntity.ok().body(new Response(200,"Login success",token));
			else
			return ResponseEntity.badRequest().body(new Response(400,"Login Unsuccessfull",null));
			}
		else
		
			throw new MailIDNotFoundException("Not a valid mail id");
		
		
		
	}
	
	
	@PostMapping("/forgotpassword")
	public ResponseEntity<Response> forgotPassword(@RequestBody ForgotDTO forgotdto) throws MailIDNotFoundException
	{
		if(utility.checkMail(forgotdto.getEmail()))
		{
			userService.forgotPassword(forgotdto);
			return ResponseEntity.ok().body(new Response(200,"Mail sent to your id",forgotdto));
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
	
	@PostMapping("files/add")
	public ResponseEntity<Response> uploadFile(@RequestPart(value = "file") MultipartFile file,
			@RequestHeader boolean enablePublicReadAccess) throws S3BucketException {
		String url=this.amazonS3ClientService.uploadFileToS3Bucket(file, enablePublicReadAccess);

		Map<String, String> responseMap = new HashMap<>();
		responseMap.put("message",
				"file [" + file.getOriginalFilename() + "] uploading request submitted successfully.");

		return ResponseEntity.ok().body(new Response( 200,"Image added successfully",url));
	}
	@GetMapping("files/get")
	public ResponseEntity<Response> uploadFile(@RequestHeader("filename") String fileName) throws S3BucketException {
		String url=this.amazonS3ClientService.getFileFromS3Bucket(fileName);
     if(url==null)
    {
	       return ResponseEntity.badRequest().body(new Response(400,"Image not Found",url));
     }
		
    else
             return ResponseEntity.ok().body(new Response( 200,"Image added successfully",url));
	}

	@PostMapping("files/delete")
	public ResponseEntity<Response> deleteFile(@RequestHeader("filename") String fileName) throws S3BucketException {
		this.amazonS3ClientService.deleteFileFromS3Bucket(fileName);

		Map<String, String> response = new HashMap<>();
		response.put("message", "file [" + fileName + "] removing request submitted successfully.");

		return ResponseEntity.ok().body(new Response( 200,"Image deleted successfully",null));
	}
	
}
