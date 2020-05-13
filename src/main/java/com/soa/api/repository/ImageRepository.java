package com.soa.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soa.api.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Integer>  {

}
