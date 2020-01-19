package com.bridgelabz.fundoonotes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.Exceptions.UserException;
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
	
	@PostMapping("/create/{jwt}")
	public ResponseEntity<Response> createNote(@RequestBody NoteDTO notedto,@PathVariable("jwt") String jwt) throws JWTTokenException,UserException
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
	
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<Response> deleteNoteById(@PathVariable("id") int id,@RequestHeader("jwt") String jwt) throws JWTTokenException,UserException
	{
		if(noteService.deleteNote(id,jwt))
		{
			return ResponseEntity.ok().body(new Response(200,"Note Deleted",id));

		}
		else
		{
			return ResponseEntity.badRequest().body(new Response(400,"problem in Deleting note",id));
		}
	}
}
