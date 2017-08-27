package com.card.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;



@Entity
@Table(name="USER")
//@Cache(usage=CacheConcurrencyStrategy.READ_ONLY, region="card")
public class User implements Serializable {

	 public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPasscode() {
		return passcode;
	}

	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3839842807618374322L;

	@Id
	    @GeneratedValue(strategy=GenerationType.AUTO)
	    @Column(name="ID",updatable=false, unique=true, nullable=false)
	private int id;
	
	@Column(name="USER_NAME")
	private String userName;
	@Column(name="PASSCODE")
	private String passcode;
	@Column(name="USER_LOCKED")
	private boolean accountNonLocked;
	
	 public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	    @JoinColumn(name="ROLE_ID")
	    private Role role;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
