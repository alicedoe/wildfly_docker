package com.alicegabbana.restserver.modelDao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;


@Entity
public class User {
	
	@Id
	@GeneratedValue (strategy= GenerationType.SEQUENCE, generator="SEQUENCE_User")
	@SequenceGenerator(name = "SEQUENCE_User", sequenceName = "SEQUENCE_User", allocationSize=25)
	private Long id;
	@ManyToOne
	@NotNull
	private Role role;
	@NotNull
	private String prenom;
	@NotNull
	private String nom;
	@NotNull
	private String email;
	@ManyToOne
	private Kidsclass kidsClass;
	@NotNull
	private String pwd;
	@NotNull
	private String token;
		
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
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
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Kidsclass getKidsClass() {
		return kidsClass;
	}
	public void setKidsClass(Kidsclass kidsClass) {
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
		return "User [id=" + id + ", role=" + role + ", prenom=" + prenom + ", nom=" + nom + ", email=" + email
				+ ", kidsClass=" + kidsClass + ", pwd=" + pwd + ", token=" + token + "]";
	}
	
}