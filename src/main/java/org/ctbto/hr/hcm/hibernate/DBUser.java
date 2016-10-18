package org.ctbto.hr.hcm.hibernate;

import java.io.Serializable;
import java.util.*;

//import org.hibernate.annotations.NamedNativeQueries;
//import org.hibernate.annotations.NamedNativeQuery;
//import org.springframework.security.core.userdetails.*;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
//import javax.persistence.Transient;

@Entity
@Table(name = "dbuser")
public class DBUser implements Serializable, UEntity {
	private static final long serialVersionUID = 1L;

	private Long 	id;
	private String 	username;
	private String	password;
	private String 	email;
	private String 	active;
	private String 	locked;
	private String 	privilege_group;
	private Date 	creation_date;
	private Date 	last_update_date;
	private String  privilege_added;
	

	//@Id
    //@Column(name = "uid")
	//@GeneratedValue(strategy = GenerationType.IDENTITY)	
	
	@Id 
	@Column(name = "uid")
	@SequenceGenerator(name="gen_seq_user",sequenceName="seq_user")
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="gen_seq_user")
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;

	}

	@Column(name = "username")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "email")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "active")
	public String getActive() {
		return active;
	}

	public void setActive(String active) {
		this.active = active;
	}

	@Column(name = "locked")
	public String getLocked() {
		return locked;
	}

	public void setLocked(String locked) {
		this.locked = locked;
	}

	@Column(name = "privilege_group")
	public String getPrivilege_group() {
		return privilege_group;
	}

	public void setPrivilege_group(String privilege_group) {
		this.privilege_group = privilege_group;
	}

	@Column(name = "creation_date")
	public Date getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(Date creation_date) {
		this.creation_date = creation_date;
	}

	@Column(name = "last_update_date")
	public Date getLast_update_date() {
		return last_update_date;
	}

	public void setLast_update_date(Date last_update_date) {
		this.last_update_date = last_update_date;
	}

	public String getPrivilege_added() {
		return privilege_added;
	}

	public void setPrivilege_added(String privilege_added) {
		this.privilege_added = privilege_added;
	}
	
	
}
