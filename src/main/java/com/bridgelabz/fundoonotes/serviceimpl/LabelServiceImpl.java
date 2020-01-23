package com.bridgelabz.fundoonotes.serviceimpl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.Exceptions.LabelExistException;
import com.bridgelabz.fundoonotes.Exceptions.LabelNotFoundException;
import com.bridgelabz.fundoonotes.Exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.dto.LabelDTO;
import com.bridgelabz.fundoonotes.model.Labels;
import com.bridgelabz.fundoonotes.model.Notes;
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
		Labels label=new Labels(labeldto.getLabelname(), user);
		labelRepository.save(label);
	    return "success";
    }
    else
   	return "existed";
    
	}



	@Override
	public boolean deleteLabelByUser(int id, String jwt) throws LabelNotFoundException {
	if(utility.validateToken(jwt))
	{
	   	int i=labelRepository.deleteLabelInUser(id);
	   	if(i!=0)
	   	return true;
	   	else
	  	return false;
	}
	else
		throw new LabelNotFoundException("Label Not Found Exception");
	}




	@Override
	public boolean renameLabelForUser(String labelname, int id, String jwt) throws LabelNotFoundException {
	if(utility.validateToken(jwt))
	{
		int i=labelRepository.renameLabel(labelname,id);
		if(i!=0)
		return true;
		else
		return false;
	  }
	else
		throw new LabelNotFoundException("Label Not Found Exception");
	
	
	}




	@Override
	public boolean displayNoteForLabel(int id, String jwt) throws NoteNotFoundException {
		if(utility.validateToken(jwt))
		{
			 
			Object[] notes=labelRepository.displayNotes(id);
			System.out.println(notes);
			if(notes!=null)
			return true;
			else
			return false;
		  }
		else
			throw new NoteNotFoundException("Note Not Found for Label Exception");
		
	}

}
