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
	public boolean saveNewNote(NoteDTO notedto, String jwt) throws JWTTokenException, UserException {
	
		UserInfo user=utility.getUser(jwt);
		if(user!=null)
		{
			System.out.println(user);
		
		Notes notes= new Notes();
		 notes.setTitle(notedto.getTitle());
		 notes.setTakeanote(notedto.getTakeanote());
		 notes.setColor(notedto.getColor());
		 notes.setUserdetails(user);
		   noteRepository.save(notes);
		
	    return true;
			
		}
		else
		{
			throw new UserException("No User for this Username");
		}
		
	}
	@Override
	public boolean deleteNote(int id, String jwt) throws UserException {
        UserInfo user=utility.getUser(jwt);
        if(user!=null)
        {
        	if(noteRepository.deleteNoteById(id)!=0)
        		return true;
        	else
        		return false;	
        }
        else
        {
			throw new UserException("No User for this Username");

        }
		
		
	}

	

	

}
