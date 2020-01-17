package com.bridgelabz.fundoonotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bridgelabz.fundoonotes.model.Notes;

public interface NoteRepository extends JpaRepository<Notes,Integer> {

	
	@Query(value="select max(id) from notes",nativeQuery=true)
	Integer returnMaxId();
	
}
