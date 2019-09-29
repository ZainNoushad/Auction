package com.auction.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "auction_user")
public class User {
	
	@Id
	@GeneratedValue
	@Column(name = "user_id")
	private Integer userId;
	
	// @NotBlank(message = "Username is required")
	@Column(name = "username")
	private String username;
	
	// @NotBlank(message = "Username is required")
	@Column(name = "password")
	private String password;
	
	// @NotBlank(message = "Email is required")
	@Column(name = "email")
	private String email;
	
	// @NotBlank(message = "Phone Number is required")
	@Column(name = "phoneumber")
	private String phoneNumber;
	
	// @NotBlank(message = "Addess is required")
	@Column(name = "address")
	private String address;
	
	// @NotBlank(message = "Select any one")
	@Column(name="question")
	private Integer question=1;
	
	// @NotBlank(message = "Answer is required")
	@Column(name="answer")
	private String answer;
	
	@Column(name = "active")
	private Integer active=1;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getQuestion() {
		return question;
	}

	public void setQuestion(Integer question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	



}

