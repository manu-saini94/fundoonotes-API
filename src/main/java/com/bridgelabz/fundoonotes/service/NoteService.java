package com.bridgelabz.fundoonotes.service;



import java.util.List;

import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.Exceptions.LabelNotFoundException;
import com.bridgelabz.fundoonotes.Exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.Exceptions.UserException;
import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.model.Notes;

public interface NoteService {

	public Notes saveNewNote(NoteDTO notedto,String jwt) throws JWTTokenException, UserException;
    
	public boolean deleteNote(int id,String jwt) throws UserException;
    
	public boolean updateLabelInNote(NoteDTO notedto,String jwt,int id) throws JWTTokenException, NoteNotFoundException;
	
	public boolean deleteLabelInsideNote(int id, int id1, String jwt) throws JWTTokenException, NoteNotFoundException;
	
	public boolean updateTitleAndTakeanote(int id,String jwt,NoteDTO notedto) throws JWTTokenException, NoteNotFoundException;
	
	public boolean updatePinForNote(int id, String jwt) throws NoteNotFoundException, JWTTokenException;
	
	public boolean updateArchiveForNote(int id, String jwt) throws NoteNotFoundException, JWTTokenException;
	
	public List<Notes> displayAllNotesByUser(String jwt) throws JWTTokenException;
	
	public List<Notes> displayPinnedNotesByUser(String jwt) throws JWTTokenException;
	
	public boolean updateColorForNote(String jwt, int id, String color) throws JWTTokenException, NoteNotFoundException;
List<Notes> displayTrashNotesByUser(String jwt) throws JWTTokenException;
	
	public boolean restoreNoteFromTrash(String jwt, int id) throws JWTTokenException;
	
	public boolean deleteNoteFromTrash(String jwt, int id) throws JWTTokenException;
	
	public boolean emptyTrashByUser(String jwt) throws JWTTokenException;

	public Object[] displaySortedByName(String jwt) throws JWTTokenException;

	public Object[] displaySortedById(String jwt) throws JWTTokenException;

	public Object[] displaySortedByDate(String jwt) throws JWTTokenException;
	
	public Notes updateToNote(NoteDTO notedto,String jwt) throws JWTTokenException, UserException, NoteNotFoundException;

	
	
	
	
	
}
