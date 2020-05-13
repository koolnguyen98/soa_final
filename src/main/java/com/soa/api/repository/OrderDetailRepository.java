package com.soa.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soa.api.entity.Order;
import com.soa.api.entity.OrderDetail;
import com.soa.api.entity.Product;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer>  {

	List<OrderDetail> findByProduct(Product product);

	List<OrderDetail> findByOrder(Order order);

}
