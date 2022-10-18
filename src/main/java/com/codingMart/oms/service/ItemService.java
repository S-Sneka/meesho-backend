package com.codingMart.oms.service;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.codingMart.oms.Status;
import com.codingMart.oms.common.APIResponse;
import com.codingMart.oms.entity.Item;
import com.codingMart.oms.entity.TrackOrderItem;
import com.codingMart.oms.exception.IdNotFoundException;
import com.codingMart.oms.exception.StatusException;
import com.codingMart.oms.repository.ItemRepository;
import com.codingMart.oms.repository.TrackOrderItemRepository;

@Service
public class ItemService {
	
	private ItemRepository repository;
	private APIResponse apiResponse;
	private TrackOrderItemRepository trackingRepository;
	private TrackOrderItemService trackService;
	private TrackOrderItem track;
	private LocalDateTime dt;
	private Item item;
	private String itemId;
	
	public ItemService(ItemRepository repository,APIResponse apiResponse,TrackOrderItemRepository trackingRepository,TrackOrderItemService trackService){
		this.repository = repository;
		this.apiResponse = apiResponse;
		this.trackingRepository = trackingRepository;
		this.trackService = trackService;
	}
	
	//Read
	public APIResponse getItemById(String itemId){
		item = repository.findByItemId(itemId);	
		if(item!=null) {
			apiResponse.setData(item);	
			apiResponse.setStatus(HttpStatus.OK.value());
			apiResponse.setError(null);
		}
		else {
				throw new IdNotFoundException(itemId);
		}
		return apiResponse;
	}
	
	public APIResponse getItems(){
		apiResponse.setData(repository.findAll());
		apiResponse.setError(null);
		apiResponse.setStatus(HttpStatus.OK.value());
		return apiResponse;
	}
	
	public APIResponse getItemByDispatcherId(long dispatcherId) {
		List<Item> items = repository.findByDispatcherId(dispatcherId);	
		if(items!=null) {
			apiResponse.setData(items);	
			apiResponse.setStatus(HttpStatus.OK.value());
			apiResponse.setError(null);
		}
		else {
				throw new IdNotFoundException(""+dispatcherId);
		}
		return apiResponse;
	}

	public APIResponse getItemsByStatus(Status status) {
		List<Item> items = repository.findByStatus(status);	
		apiResponse.setData(items);	
		apiResponse.setStatus(HttpStatus.OK.value());
		apiResponse.setError(null);
		return apiResponse;
	}
	
	public APIResponse addDispatcher(Item updItem) {
		itemId = updItem.getItemId();
		item = repository.findByItemId(itemId);
		if(item!=null) {
			int itemStatus = item.getStatus().ordinal();
			if(itemStatus==0 ||itemStatus==2 || itemStatus==3) {
				item.setDispatcherId(updItem.getDispatcherId());
				apiResponse.setData(repository.save(item));
				apiResponse.setError(null);
				apiResponse.setStatus(HttpStatus.OK.value());					
			}
			else {
				throw new StatusException("Could not add dispatcher Id to item that is "+item.getStatus());
			}
		}
		else {
			throw new IdNotFoundException(itemId);
		}
		return apiResponse;
	}
	
	public APIResponse updateItem(Item updItem) {
		itemId = updItem.getItemId();
		item = repository.findByItemId(itemId);
		if(item!=null) {
			int status = item.getStatus().ordinal();
			int updStatus = updItem.getStatus().ordinal();
			if(updStatus==2 || updStatus==3){
				if(status==0 || (status==2 && updStatus==3)){
					item.setStatus(updItem.getStatus());
					dt = LocalDateTime.now();
					track = trackService.getByItem(item);
					if(updStatus==2)
						track.setDispatchedAt(dt);
					else
						track.setShippedAt(dt);
					trackingRepository.save(track);
					item.setUpdatedAt(dt);
					apiResponse.setData(repository.save(item));
					apiResponse.setError(null);
					apiResponse.setStatus(HttpStatus.OK.value());
				}
				else {
					throw new StatusException("Could not "+ ((updStatus==2)?"dispatch":"ship") +" the item that is "+item.getStatus());
				}	
			}
			else {
				throw new StatusException("Could not update the status of the item. Item is "+item.getStatus());
			}
		}
		else {
			throw new IdNotFoundException(itemId);
		}
		return apiResponse;
	}
	
	public APIResponse deliverItem(Item updItem) {
		itemId = updItem.getItemId();
		item = repository.findByItemId(itemId);
		if(item!=null) {
			int status = item.getStatus().ordinal();
			if(status==3 || status==2){
				if(item.getDispatcherId()!=0) {
					item.setStatus(Status.DELIVERED);
					dt = LocalDateTime.now();
					track = trackService.getByItem(item);
					track.setDeliveredAt(dt);
					trackingRepository.save(track);
					item.setUpdatedAt(dt);
					apiResponse.setData(repository.save(item));
					apiResponse.setError(null);
					apiResponse.setStatus(HttpStatus.OK.value());
					trackingRepository.save(track);
				}
				else {
					throw new StatusException("Dispatcher ID is not added yet. Couldn't deliver the item.");
				}
			}
			else if(status==0) {
				throw new StatusException("Item not yet dispatched");
			}
			else {
				throw new StatusException("Could not deliver the item that is "+item.getStatus());
			}
		}
		else {
			throw new IdNotFoundException(itemId);
		}
		return apiResponse;
	}
	
	public APIResponse cancelItem(Item updItem) {
		itemId = updItem.getItemId();
		item = repository.findByItemId(itemId);
		if(item!=null) {
			int itemStatus = item.getStatus().ordinal();
			track = trackService.getByItem(item);
			if(itemStatus==0 ||itemStatus==2 || itemStatus==3) {
				item.setStatus(Status.CANCELLED);
				dt = LocalDateTime.now();
				track.setCancelledAt(dt);
				trackingRepository.save(track);
				item.setUpdatedAt(dt);
				apiResponse.setData(repository.save(item));
				apiResponse.setError(null);
				apiResponse.setStatus(HttpStatus.OK.value());
			}
			else {
				throw new StatusException("Could not cancel the item that is "+item.getStatus());
			}
		}
		else {
			throw new IdNotFoundException(itemId);
		}
		return apiResponse;
	}
	
		public APIResponse requestReturn(Item updItem) {
			itemId = updItem.getItemId();
			item = repository.findByItemId(itemId);
			if(item!=null) {
				int itemStatus = item.getStatus().ordinal();
				if(itemStatus==4){
					dt = LocalDateTime.now();
					track = trackService.getByItem(item);
					track.setRequestReturn(true);
					track.setCancelledAt(dt);
					item.setUpdatedAt(dt);
					trackingRepository.save(track);
					apiResponse.setData(repository.save(item));
					apiResponse.setError(null);
					apiResponse.setStatus(HttpStatus.OK.value());
				}
				else {
					throw new StatusException("Could not request return for the item that is "+item.getStatus());
				}
			}
			else {
				throw new IdNotFoundException(itemId);
			}
			return apiResponse;
		}
		
		public APIResponse returnItem(Item updItem) {
			itemId = updItem.getItemId();
			item = repository.findByItemId(itemId);
			if(item!=null) {
				int itemStatus = item.getStatus().ordinal();
				track = trackService.getByItem(item);
				if(track.isRequestReturn()) {
					if(itemStatus==4) {
						item.setStatus(Status.RETURNED);
						dt = LocalDateTime.now();
						track.setReturnedAt(dt);
						trackingRepository.save(track);
						item.setUpdatedAt(dt);
						apiResponse.setData(repository.save(item));
						apiResponse.setError(null);
						apiResponse.setStatus(HttpStatus.OK.value());
					}
					else {
						throw new StatusException("Could not return the item that is "+item.getStatus());
					}
				}
				else {
					throw new StatusException("Return not requested");
				}
			}
			else {
				throw new IdNotFoundException(itemId);
			}
			return apiResponse;
		}
}
