package com.bridgelabz.fundoonotes.serviceimpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonotes.Exceptions.JWTTokenException;
import com.bridgelabz.fundoonotes.Exceptions.LabelNotFoundException;
import com.bridgelabz.fundoonotes.Exceptions.NoteNotFoundException;
import com.bridgelabz.fundoonotes.Exceptions.UserException;
import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.model.Labels;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.UserInfo;
import com.bridgelabz.fundoonotes.repository.LabelRepository;
import com.bridgelabz.fundoonotes.repository.NoteRepository;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.utility.Utility;

@Service
public class NoteServiceImpl implements NoteService 
{
	
	@Autowired
	LabelRepository labelRepository;
	
	
NoteRepository noteRepository;
Utility utility;
ModelMapper mapper;

@Autowired
public NoteServiceImpl(NoteRepository noteRepository,Utility utility,ModelMapper mapper)
{
	this.noteRepository=noteRepository;
	this.utility=utility;
	this.mapper=mapper;
}
	@Override
	public boolean saveNewNote(NoteDTO notedto, String jwt) throws JWTTokenException, UserException {
	
		UserInfo user=utility.getUser(jwt);
		if(user!=null)
		{
			System.out.println(user);
		
		Notes notes= new Notes();
		 notes.setTitle(notedto.getTitle());
		 notes.setTakeanote(notedto.getTakeanote());
		 notes.setUserdetails(user);
		   noteRepository.save(notes);
		
	    return true;
			
		}
		else
		{
			throw new UserException("No User for this Username");
		}
		
	}
	@Override
	public boolean deleteNote(int id1, String jwt) throws UserException {
        UserInfo user=utility.getUser(jwt);
        if(user!=null)
        {
        	if(noteRepository.deleteNoteById(id1,user.getId())!=0)
        		return true;
        	else
        		return false;	
        }
        else
        {
			throw new UserException("No User for this Username");
        }
		
		
	}
	
	@Override
	public boolean updateLabelInNote(NoteDTO updatedto, String jwt,int id) throws JWTTokenException, NoteNotFoundException {
	if(utility.validateToken(jwt))
	{
		UserInfo user=utility.getUser(jwt);
		Labels label=noteRepository.getLabelByName(updatedto.getLabelname(),user.getId());
		Notes note=noteRepository.getNoteByNoteId(id,user.getId());
		if(label==null && note!=null)
		{
			Labels labels=new Labels(updatedto.getLabelname(),user);
		    labelRepository.save(labels);
			Labels labels1=labelRepository.getLabelByName(updatedto.getLabelname(),user.getId());
			noteRepository.saveLabelInNote(id,labels1.getId());
				
				return true;
		 }
			else
				if(label!=null && note!=null)
			{
				Labels labels1=labelRepository.getLabelByName(updatedto.getLabelname(),user.getId());
				noteRepository.saveLabelInNote(id,labels1.getId());
				return true;
			}
				
			
			else
				throw new NoteNotFoundException("Note Not Found for user");
		}
		else
		throw new JWTTokenException("Problem with your token");
		
		
	}
	
	
	
	
	@Override
	public boolean deleteLabelInsideNote(int id, int id1, String jwt) throws JWTTokenException, NoteNotFoundException {
		UserInfo user=utility.getUser(jwt);
		if(utility.validateToken(jwt))
		{
		Notes note=noteRepository.getNoteByNoteId(id,user.getId());
		if(note!=null)
		{
		noteRepository.deleteLabelInNote(note.getId(),id1);
		return true;
		}
		else
			throw new NoteNotFoundException("Note For user Not Found");
		}
		else
	    throw new JWTTokenException("Token not Found Exception");
	}
	
	
	
	
	@Override
	public boolean updatePinForNote(int id, String jwt) throws NoteNotFoundException, JWTTokenException {
		UserInfo user=null;
		boolean flag=false;
		if(utility.validateToken(jwt))
		{
			user=utility.getUser(jwt);
			Notes note=noteRepository.findNoteById(id,user.getId());
			if(note!=null)
			{
              if(!note.isPinned() && note.isArchieved())
              {
            	 int i=noteRepository.setPinning(true,user.getId(),id);
            	 int j=noteRepository.setArchieving(false,user.getId(),id);
            	 if(i!=0 && j!=0)
            		 flag=true;
            	 else
            		 flag=false;
              }
              else
            	  if(!note.isPinned() && !note.isArchieved())
              {
                 	 int i=noteRepository.setPinning(true,user.getId(),id);       
              if(i!=0)
            	  flag=true;
              else
            	  flag=false;
              }
            	else
            	 if(note.isPinned())
            	 {
                    int i=noteRepository.setPinning(false,user.getId(),id);
                if(i!=0)
        	    flag=true;
                else
        	    flag=false;
            	 }
              return flag;
			}
			else
			{
			throw new NoteNotFoundException("Note Not Found Exception");
			}
			
		}
		else
		{
	    	throw new JWTTokenException("Token Not Found Exception");	
		}
	}
	
	@Override
	public boolean updateArchieveForNote(int id, String jwt) throws NoteNotFoundException, JWTTokenException {
		UserInfo user=null;
		boolean flag=false;
		if(utility.validateToken(jwt))
		{
			user=utility.getUser(jwt);
			Notes note=noteRepository.findNoteById(id,user.getId());
			if(note!=null)
			{
              if(!note.isArchieved() &&  note.isPinned())
              {
             	 int i=noteRepository.setArchieving(true,user.getId(),id);
            	 int j=noteRepository.setPinning(false,user.getId(),id);
            	 if(i!=0 && j!=0)
            		 flag=true;
            	 else
            		 flag=false;
              }
              else
            	  if(!note.isPinned() && !note.isArchieved())
              {
                  	 int i=noteRepository.setArchieving(true,user.getId(),id);
              if(i!=0)
            	  flag=true;
              else
            	  flag=false;
              }
            	else
            	 if(note.isArchieved())
            	 {
                    int i=noteRepository.setArchieving(false,user.getId(),id);
                if(i!=0)
        	    flag=true;
                else
        	    flag=false;
            	 }
              return flag;
			}
			else
			{
			throw new NoteNotFoundException("Note Not Found Exception");
			}
			
		}
		else
		{
	    	throw new JWTTokenException("Token Not Found Exception");	
		}
		

		
		
	}
	@Override
	public List<Notes> displayAllNotesByUser(String jwt) throws JWTTokenException {
		UserInfo user=null;
		boolean flag=false;
		if(utility.validateToken(jwt))
		{
		user=utility.getUser(jwt);
		List<Notes> notes=noteRepository.getNotesByUser(user.getId());
		return notes;
		}
		else
		throw new JWTTokenException("Token Not Found Exception");	
		
	}
	
	
	@Override
	public List<Notes> displayPinnedNotesByUser(String jwt) throws JWTTokenException {
		UserInfo user=null;
		boolean flag=false;
		if(utility.validateToken(jwt))
		{
		user=utility.getUser(jwt);
		List<Notes> notes=noteRepository.getPinnedNotesByUser(user.getId());
		return notes;
		}
		else
		throw new JWTTokenException("Token Not Found Exception");	
		
	}
	
	@Override
	public boolean updateColorForNote(String jwt, int id, String color) throws JWTTokenException {
	
		UserInfo user=null;
		boolean flag=false;
		if(utility.validateToken(jwt))
		{
			user=utility.getUser(jwt);
			Notes note=noteRepository.findNoteById(id,user.getId());
			int i=noteRepository.setColorForNote(color,note.getId());
			if(i!=0)
			{
				return true;
			}
			else
				return false;
		}
		else
			throw new JWTTokenException("Token Not Found Exception");
		
	}
	
	
	
	@Override
	public List<Notes> displayTrashedNotesByUser(String jwt) throws JWTTokenException {

		UserInfo user=null;
		boolean flag=false;
		if(utility.validateToken(jwt))
		{
		user=utility.getUser(jwt);
		List<Notes> notes=noteRepository.getTrashedNotesByUser(user.getId());
		return notes;
		}
		else
		throw new JWTTokenException("Token Not Found Exception");	
	}
	
	
	
	@Override
	public boolean restoreFromTrash(String jwt) {


		return false;
	}
	

	

	

}
