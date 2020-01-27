package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.model.Labels;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.UserInfo;

@Repository
@Transactional
public interface NoteRepository extends JpaRepository<Notes,Integer> {

	@Query(value="insert into notes(note) values(:note)",nativeQuery=true)
	@Modifying
	public Integer saveNote(Notes note); 
	
	@Query(value="update notes set trashed=true where id=?1 and userdetails_id=?2",nativeQuery=true)
	@Modifying	
	public Integer deleteNoteById(int id1,int id2);
	
	@Query("from Labels where labelname=?1 and userdetails_id=?2")
	Labels getLabelByName(String labelname,int id);
	
	@Query(value="insert into notes_labels(notes_id,labels_id) values(:id1,:id2)",nativeQuery=true) 
	@Modifying
	public Integer saveLabelInNote(int id1,int id2);

	@Query(value="delete from notes_labels where notes_id=?1 and labels_id=?2",nativeQuery=true)
	@Modifying
	public void deleteLabelInNote(int id, int id1);

	@Query(value="from Notes where id=?1 and UserDetails=?2")
	public Notes findNoteById(int id1,int id2);
	
	@Query(value="update notes set pinned=?1 where userdetails_id=?2 and id=?3",nativeQuery=true)
	@Modifying
	public Integer setPinning(boolean b,int id1,int id2);
	
	@Query(value="update notes set archieved=?1 where userdetails_id=?2 and id=?3",nativeQuery=true)
	@Modifying
	public Integer setArchieving(boolean b,int id1,int id2);

	@Query(value="select * from notes where userdetails_id=?1 and pinned=0",nativeQuery=true)
	public List<Notes> getNotesByUser(int id);

	@Query(value="select * from notes where userdetails_id=?1 and pinned=1",nativeQuery=true)
	public List<Notes> getPinnedNotesByUser(int id);

	@Query(value="select * from notes where id=?1 and userdetails_id=?2",nativeQuery=true)
	public Notes getNoteByNoteId(int id, int id2);

	@Query(value="update notes set color=?1 where userdetails_id=?2",nativeQuery=true)
	@Modifying
	public int setColorForNote(String color,int id);


	
	
	
	
	
	
	
	
	
	
	
	
	
	

	

	
	
	
	

	
}
