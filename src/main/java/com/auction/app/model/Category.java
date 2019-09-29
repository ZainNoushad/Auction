package com.auction.app.model;

import javax.persistence.*;

@Entity
@Table(name="category")
public class Category {
	@Id
	@Column(name="id")
	private Integer categoryId;
	@Column(name="name")
	private String categoryName;
	@Column(name="active")
	private Integer active;
	
	
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}
	
	
	
}
