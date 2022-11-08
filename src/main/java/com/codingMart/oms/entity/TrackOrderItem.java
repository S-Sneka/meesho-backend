package com.codingMart.oms.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="TrackItem")
public class TrackOrderItem {

	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="itemId", referencedColumnName="itemId")
	private Item item;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column
	private Date orderedAt;
	
	@Column
	private Date cancelledAt;
	
	@Column
	private Date dispatchedAt;
	
	@Column
	private Date shippedAt;
	
	@Column
	private Date deliveredAt;
	
	@Column
	private Date returnedAt;
	
	@Column
	private boolean requestReturn;
	
	@Column
	private String reasonForReturn;
	
	@Column
	private boolean acceptReturn;

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getOrderedAt() {
		return orderedAt;
	}

	public void setOrderedAt(Date orderedAt) {
		this.orderedAt = orderedAt;
	}

	public Date getCancelledAt() {
		return cancelledAt;
	}

	public void setCancelledAt(Date cancelledAt) {
		this.cancelledAt = cancelledAt;
	}

	public Date getDispatchedAt() {
		return dispatchedAt;
	}

	public void setDispatchedAt(Date dispatchedAt) {
		this.dispatchedAt = dispatchedAt;
	}

	public Date getShippedAt() {
		return shippedAt;
	}

	public void setShippedAt(Date shippedAt) {
		this.shippedAt = shippedAt;
	}

	public Date getDeliveredAt() {
		return deliveredAt;
	}

	public void setDeliveredAt(Date deliveredAt) {
		this.deliveredAt = deliveredAt;
	}

	public Date getReturnedAt() {
		return returnedAt;
	}

	public void setReturnedAt(Date returnedAt) {
		this.returnedAt = returnedAt;
	}

	public boolean isRequestReturn() {
		return requestReturn;
	}

	public void setRequestReturn(boolean requestReturn) {
		this.requestReturn = requestReturn;
	}

	public String getReasonForReturn() {
		return reasonForReturn;
	}

	public void setReasonForReturn(String reasonForReturn) {
		this.reasonForReturn = reasonForReturn;
	}

	public boolean isAcceptReturn() {
		return acceptReturn;
	}

	public void setAcceptReturn(boolean acceptReturn) {
		this.acceptReturn = acceptReturn;
	}

}
