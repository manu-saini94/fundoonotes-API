package com.bridgelabz.fundoonotes.serviceimpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.Exceptions.UserException;
import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.utility.Utility;

@Service
public class NoteServiceImpl implements NoteService 
{
	
NoteRepository noteRepository;
Utility utility;
ModelMapper mapper;

@Autowired
public NoteServiceImpl(NoteRepository noteRepository,Utility utility,ModelMapper mapper)
{
	this.noteRepository=noteRepository;
	this.utility=utility;
	this.mapper=mapper;
}
	@Override
	public boolean saveNewNoteImpl(NoteDTO notedto, String jwt) throws JWTTokenException, UserException {
	
		UserInfo user=utility.getUser(jwt);
		if(user!=null)
		{
			Notes notes=new Notes(notedto.getTitle(),notedto.getTakeanote(),notedto.isArchieved(),notedto.isTrashed(),notedto.isPinned(),notedto.getReminder(),notedto.getColor());
		    noteRepository.save(notes);
		    return true;
		}
		else
		{
			throw new UserException("No User for this Username");
		}
		
	}

	

	

}
