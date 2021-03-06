package com.soa.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soa.api.entity.Color;

@Repository
public interface ColorRepository extends JpaRepository<Color, Integer>  {

}
