package com.soa.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soa.api.entity.FurnitureType;
import com.soa.api.entity.ProductType;

@Repository
public interface ProductTypeRepository extends JpaRepository<ProductType, Integer>  {

	ProductType findByAcronym(String acronym);

	List<ProductType> findByFurnitureType(FurnitureType furnitureType);

	ProductType findByName(String name);

	ProductType findByNameAndFurnitureType(String name, FurnitureType furnitureType);

	ProductType findByNameAndId(Integer id, String name);

}
