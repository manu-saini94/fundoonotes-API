package com.bridgelabz.fundoonotes.serviceimpl;

import java.io.IOException;
import java.time.LocalDateTime;
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
import com.bridgelabz.fundoonotes.repository.UserRepository;
import com.bridgelabz.fundoonotes.service.ElasticSearchService;
import com.bridgelabz.fundoonotes.service.NoteService;
import com.bridgelabz.fundoonotes.utility.Utility;

@Service
public class NoteServiceImpl implements NoteService 
{
	
	@Autowired
	LabelRepository labelRepository;
	
	@Autowired
	UserRepository userRep;
	
	@Autowired
	private ElasticSearchService elasticService;
	
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
	public Notes saveNewNote(NoteDTO notedto, String jwt) throws JWTTokenException, UserException {
	
		String user=utility.getUsernameFromToken(jwt);
		System.out.println(user);
	
	UserInfo us=userRep.findByEmail(user);
	System.out.println(us);
		
		if(us!=null)
		{
			System.out.println(us);
		
		Notes notes= new Notes();
		 notes.setTitle(notedto.getTitle());
		 notes.setTakeanote(notedto.getTakeanote());
		 notes.setPinned(notedto.isPinned());
		 notes.setTrashed(notedto.isTrashed());
		 notes.setArchived(notedto.isArchived());
		 notes.setColor(notedto.getColor());
		 notes.setReminder(notedto.getReminder());
		 notes.setCreatedTime(LocalDateTime.now());
		 
		 notes.setUserdetails(us);
		   Notes note=noteRepository.save(notes);
		   
		   try {
			elasticService.createNote(note);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	    return note;
			
		}
		else
		{
			throw new UserException("No User for this Username");
		}
		
	}
	
	@Override
	public Notes updateToNote(NoteDTO notedto, String jwt) throws JWTTokenException, UserException, NoteNotFoundException {
		Notes n=null;
		String user=utility.getUsernameFromToken(jwt);
		System.out.println(user);
	
	UserInfo us=userRep.findByEmail(user);
	System.out.println(us);
		
		if(us!=null)
		{
			Notes note=noteRepository.findNoteById(notedto.getId(),us.getId()).orElseThrow(()-> new NoteNotFoundException("Noteid Not exist"));
			
			note.setTitle(notedto.getTitle());
			note.setTakeanote(notedto.getTakeanote());
			 note.setPinned(notedto.isPinned());
			 note.setTrashed(notedto.isTrashed());
			 note.setArchived(notedto.isArchived());
			 note.setColor(notedto.getColor());
			 note.setReminder(notedto.getReminder());
			 note.setCreatedTime(LocalDateTime.now());
			   Notes note1=noteRepository.save(note);

				try {
					elasticService.updateNote(note1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(note1!=null)
				return note1;
				else
					return null;
			
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
        	Notes note=noteRepository.getNoteByNoteId(id1,user.getId());
        	note.setTrashed(true);
        	Notes updatednote=noteRepository.save(note);
        	
    			try {
    				elasticService.updateNote(updatednote);
    			} catch (Exception e) {
    				// TODO Auto-generated catch block
    				e.printStackTrace();
    			}
        		return true;
        	
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
			Notes note1=noteRepository.getNoteByNoteId(id,user.getId());
			try {
				elasticService.updateNote(note1);
			} catch (Exception e) {
				e.printStackTrace();
			}

				return true;
		 }
			else
				if(label!=null && note!=null)
			{
				Labels labels1=labelRepository.getLabelByName(updatedto.getLabelname(),user.getId());
				noteRepository.saveLabelInNote(id,labels1.getId());
				Notes note1=noteRepository.getNoteByNoteId(id,user.getId());

				try {
					elasticService.updateNote(note1);
				} catch (Exception e) {
					e.printStackTrace();
				}
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
		Notes note1=noteRepository.getNoteByNoteId(note.getId(),user.getId());
		try {
			elasticService.updateNote(note1);
		} catch (Exception e) {
			e.printStackTrace();
		}
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
			Notes note=noteRepository.findNoteById(id,user.getId()).orElseThrow(()-> new NoteNotFoundException("Note Not Found Exception")); 
            	  note.setPinned(!note.isPinned());
            	  note.setArchived(false);  	         	 		        
          	Notes notesupdate = noteRepository.save(note);
    		try {
    			elasticService.updateNote(notesupdate);
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
		}	
		else
	    	throw new JWTTokenException("Token Not Found Exception");			
		return true;
	}
	
	@Override
	public boolean updateArchiveForNote(int id, String jwt) throws NoteNotFoundException, JWTTokenException {
		UserInfo user=null;
		boolean flag=false;
		if(utility.validateToken(jwt))
		{
			user=utility.getUser(jwt);
			Notes note=noteRepository.findNoteById(id,user.getId()).orElseThrow(()-> new NoteNotFoundException("Note Not Found Exception"));
			 note.setArchived(!note.isArchived());
       	  note.setPinned(false);  	     
       	Notes notesupdate = noteRepository.save(note);
		try {
			elasticService.updateNote(notesupdate);
		} catch (Exception e) {
			e.printStackTrace();
		 }
		}
		else		
	    	throw new JWTTokenException("Token Not Found Exception");		
		return true;

		
		
	}
	@Override
	public List<Notes> displayAllNotesByUser(String jwt) throws JWTTokenException {
		UserInfo user=null;
		boolean flag=false;
		if(utility.validateToken(jwt))
		{
		user=utility.getUser(jwt);
		System.out.println(user);
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
	public boolean updateColorForNote(String jwt, int id, String color) throws JWTTokenException, NoteNotFoundException {
	
		UserInfo user=null;
		boolean flag=false;
		if(utility.validateToken(jwt))
		{
			user=utility.getUser(jwt);
			Notes note=noteRepository.findNoteById(id,user.getId()).orElseThrow(()-> new NoteNotFoundException("Noteid Not exist"));		
			note.setColor(color);	
	       	Notes notesupdate = noteRepository.save(note);
				try {
					elasticService.updateNote(notesupdate);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return true;
		
		}
		else
			throw new JWTTokenException("Token Not Found Exception");
		
	}

	
	@Override
	public List<Notes> displayTrashNotesByUser(String jwt) throws JWTTokenException {
		UserInfo user=null;
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
	public boolean restoreNoteFromTrash(String jwt, int id) throws JWTTokenException {
   		
		UserInfo user=null;
		if(utility.validateToken(jwt))
		{
		user=utility.getUser(jwt);
		Notes note=noteRepository.findNoteById(user,id);
		note.setTrashed(false);
    	Notes updatednote=noteRepository.save(note);
			try {
				elasticService.updateNote(updatednote);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(updatednote!=null)
				return true;
				else
					return false;	
		}
		else
		throw new JWTTokenException("Token Not Found Exception");
		}


	@Override
	public boolean deleteNoteFromTrash(String jwt, int id) throws JWTTokenException {
	
		UserInfo user=null;
		if(utility.validateToken(jwt))
		{
		user=utility.getUser(jwt);
		Notes note=noteRepository.findNoteById(user,id);
		int i=noteRepository.deleteNoteFromTrashByUser(user.getId(),note.getId());
        if(i!=0)
        {
			try {
				elasticService.deleteNote(note);
				System.out.println("deleted from trash"+note);
			} catch (Exception e) {
				e.printStackTrace();
			}
	    return true;
        }
        else
	    return false;
			}
		else
		throw new JWTTokenException("Token Not Found Exception");		
	
	}


	@Override
	public boolean emptyTrashByUser(String jwt) throws JWTTokenException 
	{
		UserInfo user=null;
		if(utility.validateToken(jwt))
		{
		user=utility.getUser(jwt);
		List<Notes> notes=noteRepository.getTrashedNotesByUser(user.getId());
		System.out.println("trashed notes :"+notes);
		int i=noteRepository.emptyTrashForUser(user.getId());
        if(i!=0)
        {
        	for(Notes n:notes)
	   		{
				try {
					elasticService.deleteNote(n);
					System.out.println("deleted each :"+n);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	   		}
	    return true;
        }
        else
	    return false;
			}
		else
		throw new JWTTokenException("Token Not Found Exception");	
		
		}


	@Override
	public  Object[] displaySortedByName(String jwt) throws JWTTokenException {
		UserInfo user=null;
		if(utility.validateToken(jwt))
		{
		user=utility.getUser(jwt);
		Object[] notes=noteRepository.getSortedNotesByName(user.getId());
		System.out.println(notes);
		return notes;
		}
		else
		throw new JWTTokenException("Token Not Found Exception");	

	}


	@Override
	public Object[] displaySortedById(String jwt) throws JWTTokenException {
		UserInfo user=null;
		if(utility.validateToken(jwt))
		{
		user=utility.getUser(jwt);
		Object[] notes=noteRepository.getSortedNotesById(user.getId());
		System.out.println(notes);
		return notes;
		}
		else
		throw new JWTTokenException("Token Not Found Exception");		}


	@Override
	public Object[] displaySortedByDate(String jwt) throws JWTTokenException {
		
		UserInfo user=null;
		if(utility.validateToken(jwt))
		{
		user=utility.getUser(jwt);
		Object[] notes=noteRepository.getSortedNotesByDate(user.getId());
		System.out.println(notes);
		return notes;
		}
		else
		throw new JWTTokenException("Token Not Found Exception");	}
	
	
	
	@Override
	public boolean updateTitleAndTakeanote(int id, String jwt,NoteDTO notedto) throws JWTTokenException, NoteNotFoundException {
		UserInfo user=null;
		boolean flag=false;
		if(utility.validateToken(jwt))
		{
			user=utility.getUser(jwt);
			Notes note=noteRepository.findNoteById(id,user.getId()).orElseThrow(()-> new NoteNotFoundException("Noteid Not exist"));
					
			note.setTitle(notedto.getTitle());
			note.setTakeanote(notedto.getTakeanote());
			 note.setPinned(notedto.isPinned());
			 note.setTrashed(notedto.isTrashed());
			 note.setArchived(notedto.isArchived());
			 note.setColor(notedto.getColor());
			 note.setReminder(notedto.getReminder());
			 note.setCreatedTime(LocalDateTime.now());
			   Notes note1=noteRepository.save(note);

				try {
					elasticService.updateNote(note1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(note1!=null)
				return true;
				else
					return false;
		
		}
		else
			throw new JWTTokenException("Token Not Found Exception");
	}
	
	

	

}
