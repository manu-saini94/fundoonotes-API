package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.Exceptions.LabelExistException;
import com.bridgelabz.fundoonotes.dto.LabelDTO;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.LabelService;

@RestController
@RequestMapping("/label")
public class LabelController {

	@Autowired
	private LabelService labelService;
	
	@PostMapping("/create")
	public ResponseEntity<Response> createLabel(@RequestBody LabelDTO labeldto,@RequestHeader String jwt) throws LabelExistException
	{
		String result=labelService.saveNewLabel(labeldto, jwt);
		if(result.equals("success"))
		{
			return ResponseEntity.ok().body(new Response(200,"Label Created",labeldto));
		}
		else
		if(result.equals("Invalid User"))
		{
		return ResponseEntity.badRequest().body(new Response(400,"Invalid User",labeldto));
		}
		else
		if(result.equals("existed"))
		{
			throw new LabelExistException("Label with this name already Exist");	
		}
		else
			return ResponseEntity.badRequest().body(new Response(400,"Some Problem has occured",labeldto));
	}
}
