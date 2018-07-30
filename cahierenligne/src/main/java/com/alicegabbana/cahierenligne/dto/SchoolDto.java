package com.alicegabbana.cahierenligne.dto;

public class SchoolDto {

	private Long id;
	private String schoolName;
	private String townName;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName = townName;
	}
	@Override
	public String toString() {
		return "SchoolDto [id=" + id + ", schoolName=" + schoolName + ", townName=" + townName + "]";
	}
	
}
