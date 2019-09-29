package com.auction.app.model;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name="posted_auction")
public class Auction {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer auctionId;
	@ManyToOne
	@JoinColumn(name="Posted_By", nullable=false)
	private User auctionBy;
	@Column(name="Project_Title")
	private String auctionTitle;
	@Column(name="Starting_Price")
	private double auctionStartingPrice;
	@Column(name="Posted_Date")
	private LocalDateTime  auctionPostedDate;
	@Column(name="Ending_Date")
	private LocalDateTime  auctionEndingDate;
	@ManyToOne
	@JoinColumn(name="Category_Id", nullable=false)
	private Category auctionCategory;
	@Column(name="Description")
	private String auctionDescription;
	@Column(name="active")
	private int active;
	
	@OneToMany(mappedBy = "project")
	private List<AuctionImage> images;
	
	
	public Auction(User auctionBy, String auctionTitle, double auctionStartingPrice, LocalDateTime  auctionPostedDate,
			LocalDateTime  auctionEndingDate, Category auctionCategory, String auctionDescription, int active) {
		super();
		this.auctionBy = auctionBy;
		this.auctionTitle = auctionTitle;
		this.auctionStartingPrice = auctionStartingPrice;
		this.auctionPostedDate = auctionPostedDate;
		this.auctionEndingDate = auctionEndingDate;
		this.auctionCategory = auctionCategory;
		this.auctionDescription = auctionDescription;
		this.active = active;
	}
	
	public Auction() {
		
	}
	public Integer getAuctionId() {
		return auctionId;
	}
	public void setAuctionId(Integer auctionId) {
		this.auctionId = auctionId;
	}
	public User getAuctionBy() {
		return auctionBy;
	}
	public void setAuctionBy(User auctionBy) {
		this.auctionBy = auctionBy;
	}
	public String getAuctionTitle() {
		return auctionTitle;
	}
	public void setAuctionTitle(String auctionTitle) {
		this.auctionTitle = auctionTitle;
	}
	public double getAuctionStartingPrice() {
		return auctionStartingPrice;
	}
	public void setAuctionStartingPrice(double auctionStartingPrice) {
		this.auctionStartingPrice = auctionStartingPrice;
	}
	public LocalDateTime  getAuctionPostedDate() {
		return auctionPostedDate;
	}
	public void setAuctionPostedDate(LocalDateTime  auctionPostedDate) {
		this.auctionPostedDate = auctionPostedDate;
	}
	public LocalDateTime  getAuctionEndingDate() {
		return auctionEndingDate;
	}
	public void setAuctionEndingDate(LocalDateTime  auctionEndingDate) {
		this.auctionEndingDate = auctionEndingDate;
	}
	public Category getAuctionCategory() {
		return auctionCategory;
	}
	public void setAuctionCategory(Category auctionCategory) {
		this.auctionCategory = auctionCategory;
	}
	public String getAuctionDescription() {
		return auctionDescription;
	}
	public void setAuctionDescription(String auctionDescription) {
		this.auctionDescription = auctionDescription;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}

	public List<AuctionImage> getImages() {
		return images;
	}

	public void setImages(List<AuctionImage> images) {
		this.images = images;
	}
	
	
	
}
