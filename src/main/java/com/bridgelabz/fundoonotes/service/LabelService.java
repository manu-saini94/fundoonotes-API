package com.bridgelabz.fundoonotes.service;

import com.bridgelabz.fundoonotes.dto.LabelDTO;

public interface LabelService {

	public String saveNewLabel(LabelDTO labeldto,String jwt);
	
}
