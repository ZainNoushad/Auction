package com.auction.app.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="auction_bids")
public class AuctionBids {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID")
	private Integer bidId;
	
	@ManyToOne
	@JoinColumn(name="Auction_Id", nullable=false)
	private Auction auction;
	
	@ManyToOne
	@JoinColumn(name="Customer_Id", nullable=false)
	private User customer;
	
	@Column(name="Bid_Price")
	private double bidPrice;
	
	@Column(name="Bid_On")
	private LocalDateTime bidOn;

	public Integer getBidId() {
		return bidId;
	}

	public void setBidId(Integer bidId) {
		this.bidId = bidId;
	}

	public Auction getAuction() {
		return auction;
	}

	public void setAuction(Auction auction) {
		this.auction = auction;
	}

	public User getCustomer() {
		return customer;
	}

	public void setCustomer(User customer) {
		this.customer = customer;
	}

	public double getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(double bidPrice) {
		this.bidPrice = bidPrice;
	}

	public LocalDateTime getBidOn() {
		return bidOn;
	}

	public void setBidOn(LocalDateTime bidOn) {
		this.bidOn = bidOn;
	}
	
	
	
}
