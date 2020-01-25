package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.CollaboratorDTO;

public interface CollaboratorService {


	public boolean addCollaborator(CollaboratorDTO collaboratordto, int id, String jwt);
	
	
	
}
