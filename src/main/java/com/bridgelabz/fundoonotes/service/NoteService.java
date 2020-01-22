package com.bridgelabz.fundoonotes.service;



import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.Exceptions.LabelNotFoundException;
import com.bridgelabz.fundoonotes.Exceptions.UserException;
import com.bridgelabz.fundoonotes.dto.NoteDTO;

public interface NoteService {

	public boolean saveNewNote(NoteDTO notedto,String jwt) throws JWTTokenException, UserException;
    public boolean deleteNote(int id,String jwt) throws UserException;
    public boolean updateLabelInNote(NoteDTO notedto,String jwt,int id) throws JWTTokenException;
	public boolean deleteLabelInsideNote(int id, int id1, String jwt) throws LabelNotFoundException;

}
