package com.alicegabbana.restserver.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;


@Entity
public class Homework {
	
	@Id
	@GeneratedValue (strategy= GenerationType.SEQUENCE, generator="SEQUENCE_Homework")
	@SequenceGenerator(name = "SEQUENCE_Homework", sequenceName = "SEQUENCE_Homework", allocationSize=25)
	private Long id;	
	@NotNull
	@ManyToOne
	private Subject subject;
	@NotNull
	@ManyToOne
	private User creator;
	@NotNull
	@ManyToOne
	private KidsClass kidsClass;
	private String wording;
	@ManyToMany
	private List<Tag> tag;
	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDate;
	@Temporal(TemporalType.TIMESTAMP)
	private Date endDate;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Subject getSubject() {
		return subject;
	}
	public void setSubject(Subject subject) {
		this.subject = subject;
	}
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	public KidsClass getKidsClass() {
		return kidsClass;
	}
	public void setKidsClass(KidsClass kidsClass) {
		this.kidsClass = kidsClass;
	}
	public String getWording() {
		return wording;
	}
	public void setWording(String wording) {
		this.wording = wording;
	}
	public List<Tag> getTag() {
		return tag;
	}
	public void setTag(List<Tag> tag) {
		this.tag = tag;
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
		return "Homework [id=" + id + ", subject=" + subject + ", creator=" + creator + ", kidsClass=" + kidsClass
				+ ", wording=" + wording + ", tag=" + tag + ", creationDate=" + creationDate + ", endDate=" + endDate + "]";
	}
	
}