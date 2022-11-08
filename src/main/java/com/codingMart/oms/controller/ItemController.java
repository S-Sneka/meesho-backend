package com.codingMart.oms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codingMart.oms.Status;
import com.codingMart.oms.Dto.ProcessReturn;
import com.codingMart.oms.Dto.RequestReturn;
import com.codingMart.oms.common.APIResponse;
import com.codingMart.oms.entity.Item;
import com.codingMart.oms.service.ItemService;

@RestController
@RequestMapping("/item")
public class ItemController {

	private ItemService service;
	public ItemController(ItemService service) {
		this.service = service;
	}

	@GetMapping("/all")
	public APIResponse findAllItems(){
		return service.getItems();
	}
	
	@GetMapping("/byItemId")
	public APIResponse findItemsById(@RequestBody String id) {
		return service.getItemById(id);
	}
	
	@GetMapping("/byDispatcherId")
	public APIResponse findItemsByDispatcherId(@RequestBody long id) {
		return service.getItemByDispatcherId(id);
	}
	
	@GetMapping("/byItemStatus")
	public APIResponse findByItemsStatus(@RequestBody Status status) {
		return service.getItemsByStatus(status);
	}
	
	@PutMapping("/updateStatus")
	public APIResponse updateItem(@RequestBody Item item) {
		return service.updateItem(item);
	}
	
	@PutMapping("/addDispatcher")
	public APIResponse addDispatcher(@RequestBody Item item) {
		return service.addDispatcher(item);
	}
	
//	@PutMapping("/addDeliveryDate")
//	public APIResponse addDeliveryDate(@RequestBody  idDate) {
//		return service.addDeliveryDate(idDate);
//	}
	
	@PutMapping("/deliverItem")
	public APIResponse deliver(@RequestBody Item item) {
		return service.deliverItem(item);
	}

	@PutMapping("/returnItem")
	public APIResponse returnItem(@RequestBody Item item) {
		return service.returnItem(item);
	}

	@PutMapping("/cancelItem")
	public APIResponse cancel(@RequestBody Item item) {
		return service.cancelItem(item);
	}
	
	@PutMapping("/requestReturn")
	public APIResponse requestReturn(@RequestBody RequestReturn req) {
		return service.requestReturn(req);
	}
	
	@PutMapping("/processReturn")
	public APIResponse processReturn(@RequestBody ProcessReturn ret) {
		return service.processReturn(ret);
	}
}