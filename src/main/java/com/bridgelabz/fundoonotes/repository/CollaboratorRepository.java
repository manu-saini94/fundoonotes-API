package com.bridgelabz.fundoonotes.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.Collaborator;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.UserInfo;

@Repository
@Transactional
public interface CollaboratorRepository extends JpaRepository<Collaborator,Integer> {

	@Query("from Notes where id=?1")
	 Notes getNotes(int id);
	
	@Query("from UserInfo where email=?1")
	UserInfo findByEmail(String email);

	
	@Query(value = "select * from collaborator where collaborator=?1 and notes_id=?2",nativeQuery = true)
	Object getCollaborator(String collaborator, int noteId);


	@Query(value = "delete from collaborator where collaborator=?1 and notes_id=?2",nativeQuery = true)
	@Modifying
	void deleteCollaboratorFromNote(String collaborator, int noteId);
	
	
	
	

	
	
}
