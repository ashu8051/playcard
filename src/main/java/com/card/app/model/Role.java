package com.card.app.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;




@Entity
@Table(name="ROLE")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY, region="card")
public class Role implements Serializable{

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Set<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(Set<Permission> permissions) {
		this.permissions = permissions;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 8704138402594834700L;

	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name="ROLE_ID",updatable=false, unique=true, nullable=false)
	private int id;
	
	@Column(name="ROLE_NAME")
	private String roleName;
	
	 @OneToMany(mappedBy="role", fetch=FetchType.LAZY)
	    private Set<User> users;
	 
	 @ManyToMany(fetch = FetchType.LAZY)
	    @JoinTable(
	        name="role_permission"
	        , joinColumns={
	            @JoinColumn(name="ROLE_ID")
	            }
	        , inverseJoinColumns={
	            @JoinColumn(name="PERMISSION_ID")
	            }
	        )
	    private Set<Permission> permissions;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
