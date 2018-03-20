package com.alicegabbana.restserver.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
public class School {
	
	@Id
	@GeneratedValue (strategy= GenerationType.SEQUENCE, generator="SEQUENCE_School")
	@SequenceGenerator(name = "SEQUENCE_School", sequenceName = "SEQUENCE_School", allocationSize=25)
    private Long id;
	@NotNull
	private String nom;
	@NotNull
	@ManyToOne
	private Town town;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Town getTown() {
		return town;
	}
	public void setTown(Town town) {
		this.town = town;
	}
	@Override
	public String toString() {
		return "School [id=" + id + ", nom=" + nom + ", town=" + town + "]";
	}
}