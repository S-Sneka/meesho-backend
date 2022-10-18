package com.codingMart.oms.entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="order")
public class Order{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private String orderId;
	
	@Column
	private long userId;

	@Column
	private LocalDateTime createdAt;
	
	@Column
	private LocalDateTime updatedAt;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY )
	private List<Item> items;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="price_id", referencedColumnName="id")
	private Price price;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getOrderId() {
		return orderId;
	}
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public void setUserId(long userId) {
		this.userId = userId;
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
	
	public List<Item> getItems() {
		return items;
	}
	
	public void setItems(List<Item> items) {
		this.items = items;
	}
	
	public Price getPrice() {
		return price;
	}
	
	public void setPrice(Price price) {
		this.price = price;
	}
}
