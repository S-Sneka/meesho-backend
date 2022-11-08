package com.codingMart.oms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codingMart.oms.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long>{

	@Query("SELECT o FROM Order o where o.orderId=:oId")
	Order findByOrderId(@Param(value = "oId") String orderId);

}
