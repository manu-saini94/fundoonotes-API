package com.bridgelabz.fundoonotes.service;

import java.util.List;

import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.Exceptions.LabelNotFoundException;
import com.bridgelabz.fundoonotes.Exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.dto.LabelDTO;
import com.bridgelabz.fundoonotes.model.Labels;

public interface LabelService {

	public String saveNewLabel(LabelDTO labeldto,String jwt);

	public boolean deleteLabelByUser(int id, String jwt) throws LabelNotFoundException;

	public boolean renameLabelForUser(String labelname, int id, String jwt) throws LabelNotFoundException;
	
	public boolean displayNoteForLabel(int id,String jwt) throws NoteNotFoundException;

	public List<Labels> displayAllLabels(String jwt) throws LabelNotFoundException, JWTTokenException;
	
	
	
	
	
}
