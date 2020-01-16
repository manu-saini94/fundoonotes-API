package com.bridgelabz.fundoonotes.service;

import java.util.List;

import org.omg.CORBA.UserException;

import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.dto.NoteDTO;

public interface NoteService {

	public boolean saveNewNoteImpl(NoteDTO notedto,String jwt) throws JWTTokenException, UserException;

	public void deleteNoteImpl(int id,String jwt) throws JWTTokenException, NoteNotFoundException;
	
	public boolean updateNoteImpl(UpdateNoteDTO updatedto,String jwt)throws NoteNotFoundException,JWTTokenException;
	
	public Notes getNoteImpl(int id) throws NoteNotFoundException;

	public List<Notes> getAllNoteImpl(String jwt);
	
	public List<Notes> getAllArchieveImpl(String jwt);

	public List<Notes> getAllPinnedImpl(String jwt);

	List<Notes> getAllTrashNotesImpl(String jwt);
}
