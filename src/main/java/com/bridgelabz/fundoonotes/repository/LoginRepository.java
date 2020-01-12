package com.bridgelabz.fundoonotes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import com.bridgelabz.fundoonotes.model.UserInfo;

@Repository
public interface LoginRepository extends JpaRepository<UserInfo,String> {

	@Query("from UserInfo where username=?1")
	UserInfo findByUsername(String username);

	
}
