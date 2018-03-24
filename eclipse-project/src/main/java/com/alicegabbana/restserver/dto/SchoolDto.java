package com.alicegabbana.restserver.dto;

public class SchoolDto {

	private Long id;
	private String name;
	private String townName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	
	@Override
	public String toString() {
		return "SchoolDto [id=" + id + ", name=" + name + ", townName=" + townName + "]";
	}
	
}