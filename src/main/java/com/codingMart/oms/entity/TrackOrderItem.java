package com.codingMart.oms.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="TrackItem")
public class TrackOrderItem {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private LocalDateTime orderedAt;
	
	@Column
	private LocalDateTime cancelledAt;
	
	@Column
	private LocalDateTime dispatchedAt;
	
	@Column
	private LocalDateTime shippedAt;
	
	@Column
	private LocalDateTime deliveredAt;
	
	@Column
	private LocalDateTime returnedAt;
	
	@Column
	private boolean requestReturn;
	
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="itemId", referencedColumnName="itemId")
	private Item item;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getOrderedAt() {
		return orderedAt;
	}

	public void setOrderedAt(LocalDateTime orderedAt) {
		this.orderedAt = orderedAt;
	}

	public LocalDateTime getCancelledAt() {
		return cancelledAt;
	}

	public void setCancelledAt(LocalDateTime cancelledAt) {
		this.cancelledAt = cancelledAt;
	}

	public LocalDateTime getDispatchedAt() {
		return dispatchedAt;
	}

	public void setDispatchedAt(LocalDateTime dispatchedAt) {
		this.dispatchedAt = dispatchedAt;
	}

	public LocalDateTime getShippedAt() {
		return shippedAt;
	}

	public void setShippedAt(LocalDateTime shippedAt) {
		this.shippedAt = shippedAt;
	}

	public LocalDateTime getDeliveredAt() {
		return deliveredAt;
	}

	public void setDeliveredAt(LocalDateTime deliveredAt) {
		this.deliveredAt = deliveredAt;
	}

	public LocalDateTime getReturnedAt() {
		return returnedAt;
	}

	public void setReturnedAt(LocalDateTime returnedAt) {
		this.returnedAt = returnedAt;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public boolean isRequestReturn() {
		return requestReturn;
	}

	public void setRequestReturn(boolean requestReturn) {
		this.requestReturn = requestReturn;
	}

}
