package com.todoapplication.app.entities;

import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;

@Entity(name="PASSWORD_RESET_TOKEN")
public class PasswordResetToken {
	@Id
    @Column(name = "TOKEN_ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", allocationSize = 1)
    private Long id;

    @Column(name = "TOKEN_NAME", nullable = false, unique = true)
    private String token;

    @Column(name = "EXPIRY_DATE", nullable = false)
    private Instant expiryDate;

    @OneToOne(targetEntity = Users.class,  fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "USER_ID")
    private Users userId;

    public PasswordResetToken(Long id, String token, Instant expiryDate, Users user) {
        this.id = id;
        this.token = token;
        this.expiryDate = expiryDate;
        this.userId = user;
    }

    public PasswordResetToken() {
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Users getUser() {
        return userId;
    }

    public void setUser(Users user) {
        this.userId = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
