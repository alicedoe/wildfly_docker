package com.alicegabbana.restserver.dto;

public class UserDto {

	private Long id;
	private String roleName;
	private String firstname;
	private String name;
	private String email;
	private String kidsClassName;
	private String token;
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
	public String getKidsClassName() {
		return kidsClassName;
	}
	public void setKidsClassName(String kidsClassName) {
		this.kidsClassName = kidsClassName;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	@Override
	public String toString() {
		return "UserDto [id=" + id + ", roleName=" + roleName + ", firstname=" + firstname + ", name=" + name
				+ ", email=" + email + ", kidsClassName=" + kidsClassName + ", token=" + token + ", pwd=" + pwd + "]";
	}
	
}