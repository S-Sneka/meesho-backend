package com.codingMart.oms.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import com.codingMart.oms.Status;
import com.codingMart.oms.common.APIResponse;
import com.codingMart.oms.entity.Item;
import com.codingMart.oms.entity.Order;
import com.codingMart.oms.entity.Price;
import com.codingMart.oms.entity.TrackOrderItem;
import com.codingMart.oms.exception.IdNotFoundException;
import com.codingMart.oms.repository.OrderRepository;

@Service
public class OrderService {
	
	private OrderRepository repository;
	private APIResponse apiResponse;
	private TrackOrderItemService trackService;
	private TwilioService twilioService;
	
	public OrderService(OrderRepository repository,TrackOrderItemService trackService,APIResponse apiResponse,TwilioService twilioService){
		this.repository = repository;
		this.apiResponse = apiResponse;
		this.twilioService = twilioService;
		this.trackService = trackService;
	}
	
	//Create
	public APIResponse saveOrder(Order order) {
		String s1,orderId,itemId;
		s1 = RandomStringUtils.randomAlphabetic(5);
		LocalDateTime dt = LocalDateTime.now();
		String dtId = dt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH:mm:ss"));
		
		//setOrderId
		orderId = s1+"-"+dtId;
		order.setOrderId(orderId);
		
		//SetItem
		List<Item> items = order.getItems();
		int priceOfItems = 0;
		for(int i=0;i<items.size();i++) {
			Item itm = items.get(i);
			
			//SetItemId
			s1 = RandomStringUtils.randomAlphabetic(5);
			itemId = s1+"-"+dtId;
			itm.setItemId(itemId);
		
			//SetOrderId
			itm.setOrderID(orderId);
			//SetStatus
			itm.setStatus(Status.ORDERED);
			priceOfItems += itm.getPrice();
		}
			
		//SetPrice
		Price price = order.getPrice(); 
		price.setPriceOfItems(priceOfItems);
		price.setTotalBeforeTax(price.getPriceOfItems()+price.getPostageAndPacking());	
		price.setTotal(price.getTotalBeforeTax()+price.getTax());
		price.setOrderTotal(price.getTotal()+price.getPromotionApplied()+price.getShippingCharges());
		//setResponseData
		order = repository.save(order);
		apiResponse.setData(order);	
		apiResponse.setStatus(HttpStatus.OK.value());
		apiResponse.setError(null);
		

		//SaveTracking
		for(int i=0;i<items.size();i++) {
			Item itm = items.get(i);
			TrackOrderItem track = new TrackOrderItem();
			track.setOrderedAt(itm.getCreatedAt());
			track.setItem(itm);
			track.setRequestReturn(false);
			trackService.saveTrackOrderItem(track);
		}
		
		String messege = "Your order has been placed Successfully at"+
		order.getCreatedAt()+"You can track your order using Order Id:"+order.getOrderId();
		String mobNum = "+919715382994";
		twilioService.sendSMS(messege, mobNum);
		return apiResponse;
	}
	
	//Read
	public APIResponse getOrderById(String orderId){
		Order order = repository.findByOrderId(orderId);	
		if(order!=null) {
			apiResponse.setData(order);	
			apiResponse.setStatus(HttpStatus.OK.value());
			apiResponse.setError(null);
		}
		else {
				throw new IdNotFoundException(orderId);
		}
		return apiResponse;
	}
	
	
	public APIResponse getOrders(){
		apiResponse.setData(repository.findAll());
		apiResponse.setError(null);
		apiResponse.setStatus(HttpStatus.OK.value());
		return apiResponse;
	}
}
