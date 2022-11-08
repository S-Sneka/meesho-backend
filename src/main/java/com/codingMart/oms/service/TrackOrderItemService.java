package com.codingMart.oms.service;

import org.springframework.stereotype.Service;

import com.codingMart.oms.entity.Item;
import com.codingMart.oms.entity.TrackOrderItem;
import com.codingMart.oms.repository.TrackOrderItemRepository;

@Service
public class TrackOrderItemService {
	
	private TrackOrderItemRepository trackRepository;
	
	public TrackOrderItemService(TrackOrderItemRepository trackRepository) {
		super();
		this.trackRepository = trackRepository;
	}

	public TrackOrderItem getByItem(Item item) {
		return trackRepository.findByItem(item);	
	}
	
	public TrackOrderItem saveTrackOrderItem(TrackOrderItem track) {
		return trackRepository.save(track);
	}
}
