package com.bridgelabz.fundoonotes.serviceimpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.omg.CORBA.UserException;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.service.NoteNotFoundException;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.service.Notes;
import com.bridgelabz.fundoonotes.service.UpdateNoteDTO;
import com.bridgelabz.fundoonotes.utility.Utility;

@Service
public class NoteServiceImpl implements NoteService 
{
	
NoteRepository noteRepository;
Utility utility;
ModelMapper mapper;
	
public NoteServiceImpl(NoteRepository noteRepository,Utility utility,ModelMapper mapper)
{
	this.noteRepository=noteRepository;
	this.utility=utility;
	this.mapper=mapper;
}
	@Override
	public boolean saveNewNoteImpl(NoteDTO notedto, String jwt) throws JWTTokenException, UserException {
	
		
		
	}

	@Override
	public void deleteNoteImpl(int id, String jwt) throws JWTTokenException, NoteNotFoundException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean updateNoteImpl(UpdateNoteDTO updatedto, String jwt) throws NoteNotFoundException, JWTTokenException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Notes getNoteImpl(int id) throws NoteNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Notes> getAllNoteImpl(String jwt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Notes> getAllArchieveImpl(String jwt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Notes> getAllPinnedImpl(String jwt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Notes> getAllTrashNotesImpl(String jwt) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Notes getNoteImpl(int id) throws NoteNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	

}
