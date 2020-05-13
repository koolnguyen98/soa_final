package com.soa.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soa.api.entity.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer>  {

	Account findByUserName(String userName);

}
