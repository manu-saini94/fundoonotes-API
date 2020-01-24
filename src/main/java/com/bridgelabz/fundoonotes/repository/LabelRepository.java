package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.Labels;
import com.bridgelabz.fundoonotes.model.Notes;
import com.bridgelabz.fundoonotes.model.UserInfo;

@Repository
@Transactional
public interface LabelRepository extends JpaRepository<Labels,String> {

	@Query("from Labels where labelname=?1 and userdetails_id=?2")
	Labels getLabelByName(String labelname,int id);
	
	@Query("delete from Labels where id=?1 and userdetails_id=?2")
	@Modifying 
	public Integer deleteLabelInUser(int id1, int id2);

	@Query("update Labels set labelname=?1 where id=?2 and userdetails_id=?3")
	@Modifying
	public Integer renameLabel(String labelname, int id1, int id2);
	
	@Query(value=" select * from notes where id in (select notes_id from notes_labels where labels_id=:labelID)",nativeQuery = true)
	public Object[] displayNotes(int labelID);
	
	

	
}
