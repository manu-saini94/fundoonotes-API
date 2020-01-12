package com.bridgelabz.fundoonotes.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.bridgelabz.fundoonotes.dto.UserDTO;
import com.bridgelabz.fundoonotes.utility.Utility;

@Configuration
public class UserConfiguration {

	@Bean
	public UserDTO getDto()
	{
		return new UserDTO();
	}
	
	@Bean 
	public ModelMapper modelMapper()
	{
		return new ModelMapper();
	}
	
	@Bean
	public BCryptPasswordEncoder getEncoder()
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	public Utility getUtility()
	{
		return new Utility();
	}
}
