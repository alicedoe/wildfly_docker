package com.alicegabbana.restserver.dto;

public class KidsClassDto {

	private Long id;
	private String name;
	private String levelName;
	private String schoolName;
	
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
	public String getLevelName() {
		return levelName;
	}
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	@Override
	public String toString() {
		return "KidsClassDto [id=" + id + ", name=" + name + ", levelName=" + levelName + ", schoolName=" + schoolName
				+ "]";
	}
	
}