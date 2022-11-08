package com.codingMart.oms.Dto;

import org.springframework.stereotype.Component;

@Component
public class ProcessReturn {
	private String itemId;
	private boolean accept;
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public boolean isAccept() {
		return accept;
	}
	public void setAccept(boolean accept) {
		this.accept = accept;
	}
	
}
