package com.todoapplication.app.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

@Entity(name="TODOS")
public class Todos {
	 @Id
	 @Column(name="TODO_ID")
	 @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
	 @SequenceGenerator(name = "user_seq", allocationSize = 1)
	 private int id;
	 @Column(name="TODO_NAME")
	 private String name;
	 @Column(name="DESCRIPTION")
	 private String description;
	 @Column(name="TARGET_DATE")
	 private Date targetDate;
	 @Column(name="IS_COMPLETED")
	 private boolean isCompleted;
     
	public Todos(int id, String name, String description, Date targetDate, boolean isCompleted) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.targetDate = targetDate;
		this.isCompleted = isCompleted;
	}
	public Todos() {
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return this.description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getTargetDate() {
		return this.targetDate;
	}
	public void setTargetDate(Date targetDate) {
		this.targetDate = targetDate;
	}
	public boolean isCompleted() {
		return this.isCompleted;
	}
	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
 
}
