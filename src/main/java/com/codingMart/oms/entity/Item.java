package com.codingMart.oms.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.codingMart.oms.Status;

@Entity
@Table(name="item")
public class Item implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String itemId;
	
	@Column
	private long productId;
	
	@Column
	private String orderId;
	
	@Column
	private long dispatcherId;
	
	@Column
	private int price;
	
	@Column
	private Status status;
	
	@Column
	private LocalDateTime createdAt;
	
	@Column
	private LocalDateTime updatedAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderID(String orderId) {
		this.orderId = orderId;
	}

	public long getDispatcherId() {
		return dispatcherId;
	}

	public void setDispatcherId(long dispatcherId) {
		this.dispatcherId = dispatcherId;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
}
