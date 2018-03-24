package com.alicegabbana.restserver.dto;

import java.util.Date;

public class HomeworkDto {

	private Long id;
	private String subjectName;
	private String creatorName;
	private String text;
	private Date beginDate;
	private Date endDate;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getSubjectName() {
		return subjectName;
	}
	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}
	public String getCreatorName() {
		return creatorName;
	}
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	@Override
	public String toString() {
		return "HomeworkDto [id=" + id + ", subjectName=" + subjectName + ", creatorName=" + creatorName + ", text="
				+ text + ", beginDate=" + beginDate + ", endDate=" + endDate + "]";
	}
	
}