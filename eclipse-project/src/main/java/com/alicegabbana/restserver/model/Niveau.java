package com.alicegabbana.restserver.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
public class Niveau {
	
	@Id
	@GeneratedValue (strategy= GenerationType.SEQUENCE, generator="SEQUENCE_Niveau")
	@SequenceGenerator(name = "SEQUENCE_Niveau", sequenceName = "SEQUENCE_Niveau", allocationSize=25)
    protected Long id;
	
	@NotNull
    protected String nom;
 
    public Niveau() {
    }
 
    public Niveau(String nom) {
        this.nom = nom;
    }
 
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
		return "Niveau [id=" + id + ", nom=" + nom + "]";
	}
}