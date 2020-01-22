package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.Exceptions.LabelNotFoundException;
import com.bridgelabz.fundoonotes.dto.LabelDTO;

public interface LabelService {

	public String saveNewLabel(LabelDTO labeldto,String jwt);

	public boolean deleteLabelByUser(int id, String jwt) throws LabelNotFoundException;

	public boolean renameLabelForUser(String labelname, int id, String jwt) throws LabelNotFoundException;
	
	
	
	
}
