package com.bridgelabz.fundoonotes.utility;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.repository.UserRepository;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class Utility {
	
	private String SECRET_KEY = "SECRET";
	
	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	UserRepository userRepository;
	
	public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;
	
	// retrieve Username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}
		// retrieve expiration date from jwt token
		public Date getExpirationDateFromToken(String token) {
			return getClaimFromToken(token, Claims::getExpiration);
		}
		
		public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
			final Claims claims = getAllClaimsFromToken(token);
			return claimsResolver.apply(claims);
		}
		
		private Claims getAllClaimsFromToken(String token) {
			return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
		}
		
		private Boolean isTokenExpired(String token) {
			final Date expiration = getExpirationDateFromToken(token);
			return expiration.before(new Date());
		}
		
		public String generateToken(UserDetails userdetails) {
			Map<String, Object> claims = new HashMap<String, Object>();
			return createToken(claims, userdetails.getUsername());

		}
		public String createToken(Map<String, Object> claims, String subject) {
			return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
					.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}
		
		public Boolean validateToken(String token, UserDetails userDetails) {
			final String username = getUsernameFromToken(token);
			return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		}
		
		public Boolean validateToken(String token) {
			return (!isTokenExpired(token));
		}
		
		public void sendEMail(String toEmail,String subject,String message)
		{
			SimpleMailMessage mail=new SimpleMailMessage();
			mail.setTo(toEmail);
			mail.setSubject(subject);
			mail.setText(message);
			mail.setFrom("manu.saini931222@gmail.com");
			System.out.println(mail);
			javaMailSender.send(mail);
		}
		
		public List getErrors(BindingResult result,UserDTO userdto)
		{
			List error=result.getAllErrors().stream().map(s->s.getDefaultMessage()).collect(Collectors.toList());
	        if(!userdto.getPassword().equals(userdto.getPasswordagain()))
	        {
	        	error.add("password not matching");
	        return error;
	        }
	        else
	        return error;
		}
	
		
		public boolean checkVerified(String username)
		{
			UserInfo user=userRepository.findByUsername(username);
			return user.isEmailVerified();
		}
		
		public boolean checkMail(String email)
		{
			return userRepository.findByEmail(email)!=null;
		}
		
		@Cacheable(value="cacheExec",key="#jwt")
		public UserInfo getUser(String jwt)
		{
			UserInfo user=userRepository.findByUsername(getUsernameFromToken(jwt));
			return user;
		}
		
		public boolean checkCollaborator(Notes note,String email)
		{
			if(userRepository.getCollaborator(email,note)!=null)
				return false;
			else
				return true;
			
		}
}
