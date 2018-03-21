package com.alicegabbana.restserver.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
public class KidsClass {

	@Id
	@GeneratedValue (strategy= GenerationType.SEQUENCE, generator="SEQUENCE_Kidsclass")
	@SequenceGenerator(name = "SEQUENCE_Kidsclass", sequenceName = "SEQUENCE_Kidsclass", allocationSize=25)
    private Long id;	
	@NotNull
	@ManyToOne
	private Level level;	
	@NotNull
	@ManyToOne
	private School school;
	@NotNull
	private String nom;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Level getLevel() {
		return level;
	}
	public void setLevel(Level level) {
		this.level = level;
	}
	public School getSchool() {
		return school;
	}
	public void setSchool(School school) {
		this.school = school;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	@Override
	public String toString() {
		return "Kidsclass [id=" + id + ", level=" + level + ", school=" + school + ", nom=" + nom + "]";
	}	
	
	
}