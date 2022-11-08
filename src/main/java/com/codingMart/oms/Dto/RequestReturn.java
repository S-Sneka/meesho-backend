package com.codingMart.oms.Dto;

import org.springframework.stereotype.Component;

@Component
public class RequestReturn {
	private String itemId;
	private String reason;
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
}
