package com.soa.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soa.api.entity.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>  {
	Role findByRole(String role);
}
