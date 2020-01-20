package com.bridgelabz.fundoonotes.serviceimpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.Exceptions.LabelExistException;
import com.bridgelabz.fundoonotes.dto.LabelDTO;
import com.bridgelabz.fundoonotes.model.Labels;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.repository.LabelRepository;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.service.LabelService;
import com.bridgelabz.fundoonotes.utility.Utility;

@Service
public class LabelServiceImpl implements LabelService{

	@Autowired
	private LabelRepository labelRepository;
	
	@Autowired
	private Utility utility;
	


	
	@Override
	public String saveNewLabel(LabelDTO labeldto, String jwt) {
		UserInfo user=utility.getUser(jwt);
	if(user==null)
	return "Invalid User";
	else
    if(labelRepository.getLabelByName(labeldto.getLabelname())==null)
    {
		Labels label=new Labels(labeldto.getId(), labeldto.getLabelname(), user);
		labelRepository.save(label);
	    return "success";
    }
    else
   	return "existed";
    

	}

}
