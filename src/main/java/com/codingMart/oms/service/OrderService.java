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
import com.codingMart.oms.repository.TrackOrderItemRepository;

@Service
public class OrderService {
	
	private OrderRepository repository;
	private APIResponse apiResponse;
	private TrackOrderItemRepository trackRepository;
	
	public OrderService(OrderRepository repository,TrackOrderItemRepository trackRepository,APIResponse apiResponse){
		this.repository = repository;
		this.apiResponse = apiResponse;
		this.trackRepository = trackRepository;
	}
	
	//Create
	public APIResponse saveOrder(Order order) {
		order = setOrder(order);
		//setResponseData
		apiResponse.setData(order);	
		apiResponse.setStatus(HttpStatus.OK.value());
		apiResponse.setError(null);
		repository.save(order);
		
		return apiResponse;
	}
	
	public APIResponse saveOrders(List<Order> orders) {
		for(int i=0;i<orders.size();i++) {
			orders.set(i, setOrder(orders.get(i)));
		}
		apiResponse.setData(orders);	
		apiResponse.setStatus(HttpStatus.OK.value());
		apiResponse.setError(null);
		repository.saveAll(orders);
		
		return apiResponse;
	}
	
	public Order setOrder(Order order) {
		
		String s1,orderId,itemId;
		s1 = RandomStringUtils.randomAlphabetic(5);
		LocalDateTime dt = LocalDateTime.now();
		String dtId = dt.format(DateTimeFormatter.ofPattern("dd-MM-yyyy-HH:mm:ss"));
		
		//setOrderId
		orderId = s1+"-"+dtId;
		order.setOrderId(orderId);
		
		//SetDateTime
		order.setCreatedAt(dt);
		order.setUpdatedAt(dt);
		
		//SetItem
		List<Item> items = order.getItems();
		int priceOfItems = 0;
		for(int i=0;i<items.size();i++) {
			Item itm = items.get(i);
			
			//SetItemId
			s1 = RandomStringUtils.randomAlphabetic(5);
			itemId = s1+"-"+dtId;
			itm.setItemId(itemId);
			
			//SetDateTime
			itm.setCreatedAt(dt);
			itm.setUpdatedAt(dt);
			
			//SetOrderId
			itm.setOrderID(orderId);
			//SetStatus
			itm.setStatus(Status.ORDERED);
			priceOfItems += itm.getPrice();
			
			//SaveTracking
			TrackOrderItem track = new TrackOrderItem();
			track.setOrderedAt(dt);
			track.setItem(itm);
			track.setRequestReturn(false);
			trackRepository.save(track);
		}
			
		//SetPrice
		Price price = order.getPrice(); 
		price.setPriceOfItems(priceOfItems);
		price.setTotalBeforeTax(price.getPriceOfItems()+price.getPostageAndPacking());	
		price.setTotal(price.getTotalBeforeTax()+price.getTax());
		price.setOrderTotal(price.getTotal()+price.getPromotionApplied()+price.getShippingCharges());
		return order;
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
