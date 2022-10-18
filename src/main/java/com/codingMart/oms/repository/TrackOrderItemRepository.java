package com.codingMart.oms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codingMart.oms.entity.Item;
import com.codingMart.oms.entity.TrackOrderItem;

public interface TrackOrderItemRepository extends JpaRepository<TrackOrderItem,Long>{

	@Query("SELECT t FROM TrackOrderItem t where t.item=:itm")
	TrackOrderItem findByItem(@Param(value="itm") Item item);

}
