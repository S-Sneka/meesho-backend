package com.codingMart.oms.service;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.codingMart.oms.Status;
import com.codingMart.oms.Dto.ProcessReturn;
import com.codingMart.oms.Dto.RequestReturn;
import com.codingMart.oms.common.APIResponse;
import com.codingMart.oms.entity.Item;
import com.codingMart.oms.entity.TrackOrderItem;
import com.codingMart.oms.exception.IdNotFoundException;
import com.codingMart.oms.exception.StatusException;
import com.codingMart.oms.repository.ItemRepository;

@Service
public class ItemService {
	
	private ItemRepository repository;
	private APIResponse apiResponse;
	private TrackOrderItemService trackService;
	private TwilioService twilioService;
	private TrackOrderItem track;
	private Item item;
	private String itemId;
	
	public ItemService(ItemRepository repository,APIResponse apiResponse,TrackOrderItem track,TrackOrderItemService trackService,Item item,TwilioService twilioService){
		this.repository = repository;
		this.apiResponse = apiResponse;
		this.trackService = trackService;
		this.track = track;
		this.item = item;
		this.twilioService = twilioService;
	}
	
	//Read
	public APIResponse getItemById(String itemId){
		item = repository.findByItemId(itemId);	
		if(item!=null) {
			track = trackService.getByItem(item);
			apiResponse.setData(track);	
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
			if(item.getStatus() == Status.ORDERED ||item.getStatus() == Status.DISPATCHED || item.getStatus() == Status.SHIPPED) {
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
			if(updItem.getStatus()==Status.DISPATCHED || updItem.getStatus()==Status.SHIPPED){
				if(item.getStatus()==Status.ORDERED || (item.getStatus()==Status.DISPATCHED && updItem.getStatus()==Status.SHIPPED)){
					item.setStatus(updItem.getStatus());
					item = repository.save(item);
					track = trackService.getByItem(item);
					String messege;
					if(updItem.getStatus()==Status.DISPATCHED) {
						track.setDispatchedAt(item.getUpdatedAt());
						messege = "Your order has been dispatched at "+item.getUpdatedAt()+"."
								+ "You can track your order using Item Id: "+item.getItemId();
					}
					else {
						track.setShippedAt(item.getUpdatedAt());
						messege = "Your order has been shipped at "+item.getUpdatedAt()+"."+
						"You can track your order using Item Id: "+item.getItemId();
					}
					trackService.saveTrackOrderItem(track);
					String mobNum = "+919715382994";
					twilioService.sendSMS(messege, mobNum);
					apiResponse.setData(track);
					apiResponse.setError(null);
					apiResponse.setStatus(HttpStatus.OK.value());
				}
				else {
					throw new StatusException("Could not "+ ((updItem.getStatus()==Status.DISPATCHED)?"dispatch":"ship") +
							" the item that is "+item.getStatus());
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
			if(item.getStatus()==Status.SHIPPED || item.getStatus()==Status.DISPATCHED){
				if(item.getDispatcherId()!=0) {
					item.setStatus(Status.DELIVERED);
					item = repository.save(item);
					track = trackService.getByItem(item);
					track.setDeliveredAt(item.getUpdatedAt());
					trackService.saveTrackOrderItem(track);
					String messege = "Your Order has been delivered successfully at "+item.getUpdatedAt();
					String mobNum = "+919715382994";
					twilioService.sendSMS(messege, mobNum);
					apiResponse.setData(track);
					apiResponse.setError(null);
					apiResponse.setStatus(HttpStatus.OK.value());
				}
				else {
					throw new StatusException("Dispatcher ID is not added yet. Couldn't deliver the item.");
				}
			}
			else if(item.getStatus()==Status.ORDERED) {
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
			track = trackService.getByItem(item);
			if(item.getStatus()==Status.ORDERED ||item.getStatus()==Status.DISPATCHED || item.getStatus()==Status.SHIPPED) {
				item.setStatus(Status.CANCELLED);
				item = repository.save(item);
				track.setCancelledAt(item.getUpdatedAt());
				trackService.saveTrackOrderItem(track);
				String messege = "Your Order has been cancelled at "+item.getUpdatedAt();
				String mobNum = "+919715382994";
				twilioService.sendSMS(messege, mobNum);
				apiResponse.setData(track);
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
	
		public APIResponse requestReturn(RequestReturn req) {
			itemId = req.getItemId();
			item = repository.findByItemId(itemId);
			if(item!=null) {
				if(item.getStatus()==Status.DELIVERED){
					track = trackService.getByItem(item);
					track.setRequestReturn(true);
					track.setReasonForReturn(req.getReason());
					item.setUpdatedAt(null);
					item = repository.save(item);
					trackService.saveTrackOrderItem(track);
					String messege = "Your request to 'return the order' has been placed at "+ item.getUpdatedAt()
					+ ".We will notify you on the status of your request after inspecting the queries.";
					String mobNum = "+919715382994";
					twilioService.sendSMS(messege, mobNum);
					apiResponse.setData(track);
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
		
		public APIResponse processReturn(ProcessReturn ret) {
			itemId = ret.getItemId();
			item = repository.findByItemId(itemId);
			if(item!=null) {
				if(item.getStatus()==Status.DELIVERED){
					track = trackService.getByItem(item);
					if(track.isRequestReturn()) {
						if(ret.isAccept()) {
							track.setAcceptReturn(true);
							trackService.saveTrackOrderItem(track);
							item.setUpdatedAt(null);
							item = repository.save(item);
							String messege = "Your request to 'return the order' has been accepted at "+
									item.getUpdatedAt()+ "We will collect your order within 2 0r 3 business days.";
							String mobNum = "+919715382994";
							twilioService.sendSMS(messege, mobNum);
							apiResponse.setData(track);
							apiResponse.setError(null);
							apiResponse.setStatus(HttpStatus.OK.value());
						}
						else {
							track.setAcceptReturn(false);
							trackService.saveTrackOrderItem(track);
							item.setUpdatedAt(null);
							item = repository.save(item);
							String messege = "Your request to 'return the order' has been rejected at "+item.getUpdatedAt();
							String mobNum = "+919715382994";
							twilioService.sendSMS(messege, mobNum);
							apiResponse.setData(track);
							apiResponse.setError(null);
							apiResponse.setStatus(HttpStatus.OK.value());
						}
					}
					else {
						throw new StatusException("Request to return the order has not been placed");
					}
				}
				else {
					throw new StatusException("Could not process return for the item that is "+item.getStatus());
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
				track = trackService.getByItem(item);
				if(track.isRequestReturn()) {
					if(track.isAcceptReturn()) {
						if(item.getStatus()==Status.DELIVERED) {
							item.setStatus(Status.RETURNED);
							item = repository.save(item);
							track.setReturnedAt(item.getUpdatedAt());
							trackService.saveTrackOrderItem(track);
							String messege = "Your order has been returned at "+item.getUpdatedAt()
									+ "We’ve processed your refund, and you should expect to see the amount "
									+ "credited to your account in about 3 to 5 business days.";
							String mobNum = "+918939237950";
							twilioService.sendSMS(messege, mobNum);
							apiResponse.setData(track);
							apiResponse.setError(null);
							apiResponse.setStatus(HttpStatus.OK.value());
						}
						else {
							throw new StatusException("Could not return the item that is "+item.getStatus());
						}
					}
					else {
						throw new StatusException("Request to return has been rejected.");
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
