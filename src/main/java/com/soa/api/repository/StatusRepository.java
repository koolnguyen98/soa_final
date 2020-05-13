package com.soa.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soa.api.entity.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer>  {

}
