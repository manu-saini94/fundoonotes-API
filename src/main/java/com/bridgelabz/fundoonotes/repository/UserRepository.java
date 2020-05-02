package com.bridgelabz.fundoonotes.repository;

import java.util.List;

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
public interface UserRepository extends JpaRepository<UserInfo,Integer> {

	@Query(value="insert into user_info(mobileno,firstname,lastname,email,password) values(:mobileno,:firstname,:lastname,:email,:password)",nativeQuery=true)
	@Modifying	
	public Integer SaveUser(String mobileno,String firstname,String lastname,String email,String password);
	
	
	
	
	@Query("from UserInfo where email=?1")
	UserInfo findByEmail(String email);
	
	@Query(value="update user_info set password=:password where email=:uemail",nativeQuery=true)
	@Modifying
	public void changepassword(String password,String uemail);
	
	@Query(value="update user_info set is_email_verified=true where email=:uemail",nativeQuery=true)
	@Modifying
	public Integer setVerifiedEmail(String uemail);

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	
    
	
	
	
	
}
