package com.soa.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soa.api.entity.FurnitureType;

@Repository
public interface FurnitureTypeRepository extends JpaRepository<FurnitureType, Integer>  {

	FurnitureType findByAcronym(String acronym);

	FurnitureType findByFurnitureName(String name);

}
