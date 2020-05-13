package com.soa.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soa.api.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer>  {

}
