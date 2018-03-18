package com.alicegabbana.restserver.modelDto;

public class UserDto {

	private Long id;
	private String roleName;
	private String prenom;
	private String nom;
	private String email;
	private String kidsClassName;
	private String pwd;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getKidsClassName() {
		return kidsClassName;
	}
	public void setKidsClassName(String kidsClassName) {
		this.kidsClassName = kidsClassName;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	@Override
	public String toString() {
		return "UserDto [id=" + id + ", roleName=" + roleName + ", prenom=" + prenom + ", nom=" + nom + ", email="
				+ email + ", kidsClassName=" + kidsClassName + ", pwd=" + pwd + "]";
	}
	
}