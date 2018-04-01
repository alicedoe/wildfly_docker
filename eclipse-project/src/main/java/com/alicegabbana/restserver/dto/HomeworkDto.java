package com.alicegabbana.restserver.dto;

import java.util.Date;

public class HomeworkDto {

	private Long id;
	private String subjectName;
	private Long creatorId;
	private Long kidsClassId;
	private String content;
	private Date creationDate;
	private Date endDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Long getKidsClassId() {
		return kidsClassId;
	}
	public void setKidsClassId(Long kidsClassId) {
		this.kidsClassId = kidsClassId;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public Long getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(Long creatorId) {
		this.creatorId = creatorId;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Override
	public String toString() {
		return "HomeworkDto [id=" + id + ", subjectName=" + subjectName + ", creatorId=" + creatorId + ", kidsClassId="
				+ kidsClassId + ", content=" + content + ", creationDate=" + creationDate + ", endDate=" + endDate + "]";
	}
	
	
	
}