package com.bridgelabz.fundoonotes.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.model.UserInfo;

@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserInfo,Integer> {

	@Query(value="insert into user_info(id,username,firstname,lastname,email,password) values(:id,:username,:firstname,:lastname,:email,:password)",nativeQuery=true)
	@Modifying	
	public Integer SaveUser(int id,String username,String firstname,String lastname,String email,String password);

	@Query("from UserInfo where username=?1")
    UserInfo findByUsername(String username);
	
	@Query("from UserInfo where email=?1")
	UserInfo findByEmail(String email);
	
	@Query(value="update user_info set password=:password where username=:username",nativeQuery=true)
	@Modifying
	public void changepassword(String password,String username);
	
	@Query(value="update user_info set isemailverified=true where username=:username",nativeQuery=true)
	@Modifying
	public void setVerifiedEmail(String username);
	
	
	
}
