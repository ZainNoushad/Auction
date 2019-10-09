package com.auction.app.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "completed_auction")
public class CompletedAuction {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "auction_winner_id")
	private User auctionWonBy;
	
	@ManyToOne
	@JoinColumn(name="auction_id")
	private Auction auction;
	
	@Column(name="completed_on")
	private LocalDateTime auctionCompletedOn;
	
	@Column(name="winning_bid_price")
	private Double winningBidPrice;
	
	@Column(name="active")
	private Integer active;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getAuctionWonBy() {
		return auctionWonBy;
	}

	public void setAuctionWonBy(User auctionWonBy) {
		this.auctionWonBy = auctionWonBy;
	}

	public Auction getAuction() {
		return auction;
	}

	public void setAuction(Auction auction) {
		this.auction = auction;
	}

	public LocalDateTime getAuctionCompletedOn() {
		return auctionCompletedOn;
	}

	public void setAuctionCompletedOn(LocalDateTime auctionCompletedOn) {
		this.auctionCompletedOn = auctionCompletedOn;
	}

	public Double getWinningBidPrice() {
		return winningBidPrice;
	}

	public void setWinningBidPrice(Double winningBidPrice) {
		this.winningBidPrice = winningBidPrice;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}
	
	
	
	
}
