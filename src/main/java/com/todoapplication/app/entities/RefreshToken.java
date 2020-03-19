package com.todoapplication.app.entities;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name="REFRESH_TOKEN")
public class RefreshToken {
	
	@Id
	@Column(name="REFRESH_TOKEN_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", allocationSize = 1)
	private int id;
	@Column(name="TOKEN", nullable=false,unique=true)
	private String token;
	@Column(name="REFRESH_COUNT")
	private int refreshCount;
	@Column(name="EXPIRY_DATE")
	private Instant expiryDate;

	public RefreshToken() {
	}

	public RefreshToken(int id, String token, int refreshCount, Instant expiryDate) {
		super();
		this.id = id;
		this.token = token;
		this.refreshCount = refreshCount;
		this.expiryDate = expiryDate;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getRefreshCount() {
		return this.refreshCount;
	}

	public void setRefreshCount(int refreshCount) {
		this.refreshCount = refreshCount;
	}

	public Instant getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(Instant expiryDate) {
		this.expiryDate = expiryDate;
	}

}
