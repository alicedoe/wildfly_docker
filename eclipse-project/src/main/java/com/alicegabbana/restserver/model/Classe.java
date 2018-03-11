package com.alicegabbana.restserver.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
public class Classe {

	@Id
	@GeneratedValue (strategy= GenerationType.SEQUENCE, generator="SEQUENCE_Classe")
	@SequenceGenerator(name = "SEQUENCE_Classe", sequenceName = "SEQUENCE_Classe", allocationSize=25)
    private Long id;	
	@NotNull
	@ManyToOne
	private Niveau niveau;	
	@NotNull
	@ManyToOne
	private Ville ville;
	@NotNull
	private String nom;	
	
	public Ville getVille() {
		return ville;
	}

	public void setVille(Ville ville) {
		this.ville = ville;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Niveau getNiveau() {
		return niveau;
	}

	public void setNiveau(Niveau niveau) {
		this.niveau = niveau;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	
	@Override
	public String toString() {
		return "Classe [id=" + id + ", niveau=" + niveau + ", ville=" + ville + ", nom=" + nom + "]";
	}

}