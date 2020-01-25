package com.bridgelabz.fundoonotes.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bridgelabz.fundoonotes.dto.CollaboratorDTO;
import com.bridgelabz.fundoonotes.response.Response;
import com.bridgelabz.fundoonotes.service.CollaboratorService;

@RestController
@RequestMapping("/collab")
public class CollaboratorController {

	
	CollaboratorService collaboratorService;
	
	@PostMapping("/add")
	public ResponseEntity<Response> addCollaboratorForNote(@RequestBody CollaboratorDTO collaboratordto,@RequestHeader("id") int id,@RequestHeader("jwt") String jwt)
	{
		if(collaboratorService.addCollaborator(collaboratordto,id,jwt))
		{
			return ResponseEntity.ok().body(new Response(200,"Collaborator Added to Note",id));
		}
		else
		{
			return ResponseEntity.badRequest().body(new Response(400,"Problem in Adding Collaborator",id));

		}
		
	}

}
