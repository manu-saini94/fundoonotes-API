package com.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.Exceptions.LabelNotFoundException;
import com.bridgelabz.fundoonotes.Exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.Exceptions.UserException;
import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.model.Notes;
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
		if(noteService.saveNewNote(notedto,jwt))
		{
			return ResponseEntity.ok().body(new Response(200,"Note Created",notedto));
		}
		else
		{
			return ResponseEntity.badRequest().body(new Response(400,"problem in creating note",notedto));
		}
		
		
	}
	
	@GetMapping("/displayAll/{jwt}")
	public ResponseEntity<Response> displayAllNotesForUser(@PathVariable("jwt") String jwt) throws JWTTokenException
	{
		List<Notes> list=noteService.displayAllNotesByUser(jwt);
		
	if(list!=null)
	{
		return ResponseEntity.ok().body(new Response(200,"Notes Displayed ",list));

	}
	else
	{
		return ResponseEntity.badRequest().body(new Response(400,"problem in Displaying note",list));
	}
		
	}
	
	@GetMapping("/displaypinned/{jwt}")
	public ResponseEntity<Response> displayPinnedNotesForUser(@PathVariable("jwt") String jwt) throws JWTTokenException
	{
		List<Notes> list=noteService.displayPinnedNotesByUser(jwt);
		
		if(list!=null)
		{
			return ResponseEntity.ok().body(new Response(200,"Notes Displayed ",list));
		}
		else
		{
			return ResponseEntity.badRequest().body(new Response(400,"problem in Displaying note",list));
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
	
	@PutMapping("/update/label/{id}")
	public ResponseEntity<Response> updateNoteById(@PathVariable("id") int id,@RequestBody NoteDTO updatedto,@RequestHeader("jwt") String jwt) throws JWTTokenException
	{
	   boolean b=noteService.updateLabelInNote(updatedto,jwt,id);
	   if(b==true)
	   {
		   return ResponseEntity.ok().body(new Response(200,"Label created inside note",id));
	   }
	   else
	   {
		   return ResponseEntity.badRequest().body(new Response(400,"problem in creating label",id));
	   }
		
		
	}
	
	@DeleteMapping("/delete/label/{id}")
	public ResponseEntity<Response> deleteLabelFromNote(@PathVariable("id") int id,@RequestHeader("id1") int id1,@RequestHeader("jwt") String jwt) throws LabelNotFoundException
	{
		boolean b=noteService.deleteLabelInsideNote(id,id1,jwt);
		if(b==true)
		{
			return ResponseEntity.ok().body(new Response(200,"Label deleted inside note",id1));
		}
		else
		{
			   return ResponseEntity.badRequest().body(new Response(400,"problem in deleting label",id));

		}
		
	}
	
	@PutMapping("/update/pin/{id}")
	public ResponseEntity<Response> updateNoteWithPin(@PathVariable("id") int id,@RequestHeader("jwt") String jwt) throws NoteNotFoundException, JWTTokenException
	{
	 boolean b=noteService.updatePinForNote(id,jwt);
	 if(b==true)
		{
			return ResponseEntity.ok().body(new Response(200,"Note pin updated",id));
		}
		else
		{
			   return ResponseEntity.badRequest().body(new Response(400,"problem in updating pin",id));

		}		
	}
	
	@PutMapping("/update/archieve/{id}")
	public ResponseEntity<Response> updateNoteWithArchieve(@PathVariable("id") int id,@RequestHeader("jwt") String jwt) throws NoteNotFoundException, JWTTokenException
	{
	 boolean b=noteService.updateArchieveForNote(id,jwt);
	 if(b==true)
		{
			return ResponseEntity.ok().body(new Response(200,"Note Archieve updated",id));
		}
		else
		{
			   return ResponseEntity.badRequest().body(new Response(400,"problem in updating Archieve",id));

		}		
	}

	
}
