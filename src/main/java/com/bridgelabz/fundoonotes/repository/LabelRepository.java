package com.bridgelabz.fundoonotes.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.Labels;
import com.bridgelabz.fundoonotes.model.UserInfo;

@Repository
@Transactional
public interface LabelRepository extends JpaRepository<Labels,String> {

	@Query("from Labels where labelname=?1")
	Labels getLabelByName(String labelname);
	
	

	
}
