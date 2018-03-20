package com.alicegabbana.restserver.entity;

import java.util.Date;
import java.util.List;

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
	private Matiere matiere;
	@NotNull
	@ManyToOne
	private User creator;
	@NotNull
	private String text;
	@NotNull
	@ManyToMany
	private List<Tag> tag;
	@Temporal(TemporalType.TIMESTAMP)
	private Date creation;
	@Temporal(TemporalType.TIMESTAMP)
	private Date end;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Matiere getMatiere() {
		return matiere;
	}
	public void setMatiere(Matiere matiere) {
		this.matiere = matiere;
	}
	public User getCreator() {
		return creator;
	}
	public void setCreator(User creator) {
		this.creator = creator;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public List<Tag> getTag() {
		return tag;
	}
	public void setTag(List<Tag> tag) {
		this.tag = tag;
	}
	public Date getCreation() {
		return creation;
	}
	public void setCreation(Date creation) {
		this.creation = creation;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	@Override
	public String toString() {
		return "Devoir [id=" + id + ", matiere=" + matiere + ", creator=" + creator + ", text=" + text + ", tag=" + tag
				+ ", creation=" + creation + ", end=" + end + "]";
	}
 
}