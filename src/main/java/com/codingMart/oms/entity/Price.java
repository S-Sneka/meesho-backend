package com.codingMart.oms.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="price")
public class Price {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private int priceOfItems;
	
	@Column
	private int postageAndPacking;
	
	@Column
	private int totalBeforeTax;
	
	@Column
	private int tax;
	
	@Column
	private int total;
	
	@Column
	private int promotionApplied;
	
	@Column
	private int shippingCharges;
	
	@Column
	private int orderTotal;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getPriceOfItems() {
		return priceOfItems;
	}

	public void setPriceOfItems(int priceOfItems) {
		this.priceOfItems = priceOfItems;
	}

	public int getPostageAndPacking() {
		return postageAndPacking;
	}

	public void setPostageAndPacking(int postageAndPacking) {
		this.postageAndPacking = postageAndPacking;
	}

	public int getTotalBeforeTax() {
		return totalBeforeTax;
	}

	public void setTotalBeforeTax(int totalBeforeTax) {
		this.totalBeforeTax = totalBeforeTax;
	}

	public int getTax() {
		return tax;
	}

	public void setTax(int tax) {
		this.tax = tax;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPromotionApplied() {
		return promotionApplied;
	}

	public void setPromotionApplied(int promotionApplied) {
		this.promotionApplied = promotionApplied;
	}

	public int getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(int orderTotal) {
		this.orderTotal = orderTotal;
	}

	public int getShippingCharges() {
		return shippingCharges;
	}

	public void setShippingCharges(int shippingCharges) {
		this.shippingCharges = shippingCharges;
	}
}
