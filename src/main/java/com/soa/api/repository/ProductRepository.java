package com.soa.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soa.api.entity.Brand;
import com.soa.api.entity.Product;
import com.soa.api.entity.ProductType;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>  {

	List<Product> findByProductType(ProductType productType);

	List<Product> findByBrand(Brand brand);

}
