package com.codingMart.oms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingMart.oms.common.APIResponse;
import com.codingMart.oms.entity.Order;
import com.codingMart.oms.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

	private OrderService service;
	public OrderController(OrderService service) {
		this.service = service;
	}
	
	@PostMapping("/add")
	public APIResponse addCustomer(@RequestBody Order order){
		return service.saveOrder(order);
	}
	
	@PostMapping("/addMany")
	public APIResponse addOrders(@RequestBody List<Order> orders) {
		return service.saveOrders(orders);
	}
	
	@GetMapping("/all")
	public APIResponse findAllOrders(){
		return service.getOrders();
	}
	
	@GetMapping("/byId")
	public APIResponse findOrderById(@RequestBody String id) {
		return service.getOrderById(id);
	}
}