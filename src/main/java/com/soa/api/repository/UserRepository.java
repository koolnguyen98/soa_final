package com.soa.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soa.api.entity.Account;
import com.soa.api.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>  {

	User findByAccount(Account account);

	User findByEmail(String email);
	
	User findUserByAccountId(Integer id);


}
