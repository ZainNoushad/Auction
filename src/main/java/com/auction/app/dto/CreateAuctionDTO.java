package com.auction.app.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.web.multipart.MultipartFile;

import com.auction.app.model.Auction;
import com.auction.app.model.Category;

public class CreateAuctionDTO {
	
	private String title;
	
	private double startingPrice;
	
	private String endingDate;

	private int category;
	
	private String description;

	private MultipartFile[] multiPartFiles;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public double getStartingPrice() {
		return startingPrice;
	}

	public void setStartingPrice(double startingPrice) {
		this.startingPrice = startingPrice;
	}

	public String getEndingDate() {
		return endingDate;
	}

	public void setEndingDate(String endingDate) {
		this.endingDate = endingDate;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public MultipartFile[] getMultiPartFiles() {
		return multiPartFiles;
	}

	public void setMultiPartFiles(MultipartFile[] multiPartFiles) {
		this.multiPartFiles = multiPartFiles;
	}
	
	public boolean isDataValid() {
		if(title.isEmpty() || description.isEmpty() || startingPrice<=0 || category<=0 || endingDate==null || multiPartFiles==null)
			return false;
		
		return true;
	}
	

	
}
