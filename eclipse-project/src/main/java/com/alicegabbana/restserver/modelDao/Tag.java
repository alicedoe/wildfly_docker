package com.alicegabbana.restserver.modelDao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
public class Tag {
	
	@Id
	@GeneratedValue (strategy= GenerationType.SEQUENCE, generator="SEQUENCE_Tag")
	@SequenceGenerator(name = "SEQUENCE_Tag", sequenceName = "SEQUENCE_Tag", allocationSize=25)
	private Long id;
	
	@NotNull
	private String nom;

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

	@Override
	public String toString() {
		return "Tag [id=" + id + ", nom=" + nom + "]";
	}
	
	
}