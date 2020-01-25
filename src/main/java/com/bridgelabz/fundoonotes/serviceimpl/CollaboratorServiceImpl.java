package com.bridgelabz.fundoonotes.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.dto.CollaboratorDTO;
import com.bridgelabz.fundoonotes.model.Collaborator;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.repository.CollaboratorRepository;
import com.bridgelabz.fundoonotes.service.CollaboratorService;
import com.bridgelabz.fundoonotes.utility.Utility;

@Service
public class CollaboratorServiceImpl implements CollaboratorService{

	CollaboratorRepository collaboratorRepository;
	Utility utility;
	
	@Autowired
	public CollaboratorServiceImpl(CollaboratorRepository collaboratorRepository, Utility utility) {
		super();
		this.collaboratorRepository = collaboratorRepository;
		this.utility = utility;
	}
	
	@Override
	public boolean addCollaborator(CollaboratorDTO collaboratordto, int id, String jwt) {
    UserInfo user=utility.getUser(jwt);
		if(collaboratorRepository.findByEmail(collaboratordto.getCollaborator())!=null && !user.getEmail().equals(collaboratordto.getCollaborator()))
		{
			Notes note = collaboratorRepository.getNotes(collaboratordto.getNoteId());
			if (utility.checkCollaborator(note, collaboratordto.getCollaborator())) {
				Collaborator collaborator = new Collaborator(collaboratordto.getCollaborator(), note);
				collaboratorRepository.save(collaborator);
			}
			else
				throw new EmailAlreadyExist("Mail Id Already Callaborated");
		}
		else
			throw new UserException("Enter a Valid Email Id")
	
	}
	
	
	
}
