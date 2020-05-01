package com.bridgelabz.fundoonotes.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.dto.NoteDTO;
import com.bridgelabz.fundoonotes.model.Collaborator;
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

	@Query(value="from Notes where id=?1 and userdetails_id=?2")
	public Optional<Notes> findNoteById(int id1,int id2);
	
	@Query(value="update notes set pinned=?1 where userdetails_id=?2 and id=?3",nativeQuery=true)
	@Modifying
	public Integer setPinning(boolean b,int id1,int id2);
	
	@Query(value="update notes set archived=?1 where userdetails_id=?2 and id=?3",nativeQuery=true)
	@Modifying
	public Integer setArchiving(boolean b,int id1,int id2);
	
	@Query(value="update notes set title=?1,takeanote=?2,pinned=?3,archived=?4 where userdetails_id=?5 and id=?6",nativeQuery=true)
	@Modifying
	public Integer setTitleAndTakeanote(String title,String takeanote,boolean pinned,boolean archived,int id1,int id2);

	@Query(value="select * from notes where userdetails_id=?1",nativeQuery=true)
	public List<Notes> getNotesByUser(int id);

	@Query(value="select * from notes where userdetails_id=?1 and pinned=1",nativeQuery=true)
	public List<Notes> getPinnedNotesByUser(int id);

	@Query(value="select * from notes where id=?1 and userdetails_id=?2",nativeQuery=true)
	public Notes getNoteByNoteId(int id, int id2);

	@Query(value="update notes set color=?1 where id=?2",nativeQuery=true)
	@Modifying
	public int setColorForNote(String color,int id);
	
    @Query(value="select * from user_info where email=?1",nativeQuery=true)
	public Object findByName(String user);

    @Query("from Collaborator where collaborator=?1 and notes=?2")
	Collaborator getCollaborator(String email,Notes notes);
	
	@Query(value="select * from notes where userdetails_id=?1 and trashed=1",nativeQuery=true)
	public List<Notes> getTrashedNotesByUser(int id);

	@Query(value="from Notes where userdetails=?1 and id=?2")
	public Notes findNoteById(UserInfo id1,int id2);
	
	@Query(value="update notes set trashed=0 where userdetails_id=?1 and id=?2",nativeQuery=true)
	@Modifying
	public int restoreNoteByUser(int id, int id2);

	@Query(value="delete from notes where userdetails_id=?1 and id=?2",nativeQuery=true)
	@Modifying
	public int deleteNoteFromTrashByUser(int id, int id2);

	@Query(value="delete from notes where userdetails_id=?1 and trashed=1",nativeQuery=true)
	@Modifying
	public int emptyTrashForUser(int id);

	@Query(value="select * from notes where userdetails_id=?1 order by title",nativeQuery=true)
	public Object[] getSortedNotesByName(int id);

	
	@Query(value="select * from notes where userdetails_id=?1 order by id",nativeQuery=true)
	public Object[] getSortedNotesById(int id);

	@Query(value="select * from notes where userdetails_id=?1 order by created_time",nativeQuery=true)
	public Object[] getSortedNotesByDate(int id);

	@Query(value="update notes set title=?1,takeanote=?2,color=?3,created_time=?4,reminder=?5 where id=?6 and userdetails_id=?7",nativeQuery=true)
	@Modifying
	public int updateNote(String title, String takeanote, String color, LocalDateTime createdtime,
			LocalDateTime reminder,int id1,int id2);
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	

	
	
	
	

	
}
