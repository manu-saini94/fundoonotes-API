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
public interface UserRepository extends JpaRepository<UserInfo,Integer> {

	@Query(value="insert into user_info(username,firstname,lastname,email,password) values(:username,:firstname,:lastname,:email,:password)",nativeQuery=true)
	@Modifying	
	public Integer SaveUser(String username,String firstname,String lastname,String email,String password);

	@Query("from UserInfo where username=?1")
    UserInfo findByUsername(String username);
	
	@Query("from UserInfo where email=?1")
	UserInfo findByEmail(String email);
	
	@Query(value="update user_info set password=:password where username=:username",nativeQuery=true)
	@Modifying
	public void changepassword(String password,String username);
	
	@Query(value="update user_info set is_email_verified=true where username=:username",nativeQuery=true)
	@Modifying
	public void setVerifiedEmail(String username);

	@Query("from Collaborator where collaborator=?1 and notes=?2")
	Collaborator getCollaborator(String email,Notes notes);
	
	
	
	
}
