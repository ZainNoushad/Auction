package com.auction.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="auction_images")
public class AuctionImage {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="ID")
	private Integer imageId;
	@ManyToOne
	@JoinColumn(name="auction_id", nullable=false)
	private Auction project;
	@Column(name="image_path")
	private String path;
	
	public AuctionImage(Auction project, String path) {
		super();
		this.project = project;
		this.path = path;
	}
	public AuctionImage() {

	}
	public Integer getImageId() {
		return imageId;
	}
	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}
	public Auction getProject() {
		return project;
	}
	public void setProject(Auction project) {
		this.project = project;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	
	
	
	
	
}
