package com.card.app.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name="USER_ATTEMPTS")
//@Cache(usage=CacheConcurrencyStrategy.READ_ONLY, region="card")
public class UserAttempts implements Serializable{

	public UserAttempts() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1256300533052594198L;
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="USER_ATTEMPS_ID",updatable=false, unique=true, nullable=false)
	private int id;
	@Column(name="USER_NAME")
	private String username;
	
	@Column(name="LOGIN_ATTEMPTS")
	private int attempts;
	
	@Column(name="LST_UPDT_DTM")
	private Date lastModified;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getAttempts() {
		return attempts;
	}
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	public Date getLastModified() {
		return lastModified;
	}
	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}
	
	
}
