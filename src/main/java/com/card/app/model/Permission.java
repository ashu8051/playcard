package com.card.app.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;



@Entity
@Table(name="PERMISSION")
@Cache(usage=CacheConcurrencyStrategy.READ_ONLY, region="card")
public class Permission implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2985408000210147595L;

	 @Id
	    @GeneratedValue(strategy=GenerationType.AUTO)
	    @Column(name="PERMISSION_ID",updatable=false, unique=true, nullable=false)
	    private int id;

	    @Column(name="PERMISSION_NAME")
		private String name;

	

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = "ROLE_"+name;
		}

		public Set<Role> getRoles() {
			return roles;
		}

		public void setRoles(Set<Role> roles) {
			this.roles = roles;
		}

		@ManyToMany(mappedBy="permissions", fetch=FetchType.LAZY)
		private Set<Role> roles;
}
