package com.soa.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.soa.api.entity.Order;
import com.soa.api.entity.Status;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>  {

	List<Order> findByStatus(Status status);

}
