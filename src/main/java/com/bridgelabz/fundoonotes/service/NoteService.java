package com.bridgelabz.fundoonotes.service;

import java.util.List;


import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.Exceptions.UserException;
import com.bridgelabz.fundoonotes.dto.NoteDTO;

public interface NoteService {

	public boolean saveNewNoteImpl(NoteDTO notedto,String jwt) throws JWTTokenException, UserException;


}
