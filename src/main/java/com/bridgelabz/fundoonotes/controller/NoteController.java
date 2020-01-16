package com.bridgelabz.fundoonotes.controller;

import org.omg.CORBA.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.utility.Utility;

@RestController
@RequestMapping("/note")
public class NoteController {

	@Autowired
	NoteService noteService;
	
	@Autowired
	Utility utility;
	
	@PostMapping("/new")
	public ResponseEntity<Response> newNote(@RequestBody NoteDTO notedto,@RequestHeader("jwt") String jwt) throws JWTTokenException, UserException
	{
		if(noteService.saveNewNoteImpl(notedto,jwt))
		{
			return ResponseEntity.ok().body(new Response(200,"Note Created",notedto));
		}
		else
		{
			return ResponseEntity.badRequest().body(new Response(400,"problem in creating note",notedto));
		}
		
		
	}
}
