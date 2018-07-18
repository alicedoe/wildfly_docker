package com.alicegabbana.cahierenligne.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;


@Entity
public class User implements Serializable {
	
	private static final long serialVersionUID = 8718563851700245805L;
	
	@Id
	@GeneratedValue (strategy= GenerationType.SEQUENCE, generator="SEQUENCE_User")
	@SequenceGenerator(name = "SEQUENCE_User", sequenceName = "SEQUENCE_User", allocationSize=25)
	private Long id;
	@ManyToOne
	@NotNull
	private Role role;
	@NotNull
	private String firstname;
	@NotNull
	private String name;
	@NotNull
	private String email;
	@ManyToOne
	private KidsClass kidsClass;
	@NotNull
	private String pwd;
	@NotNull
	private String token;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public KidsClass getKidsClass() {
		return kidsClass;
	}
	public void setKidsClass(KidsClass kidsClass) {
		this.kidsClass = kidsClass;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", role=" + role + ", firstname=" + firstname + ", name=" + name + ", email=" + email
				+ ", kidsClass=" + kidsClass + ", pwd=" + pwd + ", token=" + token + "]";
	}
}