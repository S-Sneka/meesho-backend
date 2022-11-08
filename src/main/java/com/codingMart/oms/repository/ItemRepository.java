package com.codingMart.oms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.codingMart.oms.Status;
import com.codingMart.oms.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item,Long>{

	@Query("SELECT i FROM Item i where i.itemId=:iId")
	Item findByItemId(@Param(value = "iId") String itemId);
	
	@Query("SELECT i FROM Item i where i.dispatcherId=:dId")
	List<Item> findByDispatcherId(@Param(value="dId") long dispatcherId);

	@Query("SELECT i FROM Item i where i.status=:sts")
	List<Item> findByStatus(@Param(value="sts") Status status);

}
