package com.soa.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soa.api.entity.ShoppingCart;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Integer>  {

	List<ShoppingCart> findByShoppingCartKeyAccountId(Integer id);

	List<ShoppingCart> findByShoppingCartKeyProductId(Integer id);

	ShoppingCart findByShoppingCartKeyAccountIdAndShoppingCartKeyProductId(Integer id, Integer id2);

}
