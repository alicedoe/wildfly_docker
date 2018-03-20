package com.alicegabbana.restserver.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
public class Level {
	
	@Id
	@GeneratedValue (strategy= GenerationType.SEQUENCE, generator="SEQUENCE_Level")
	@SequenceGenerator(name = "SEQUENCE_Level", sequenceName = "SEQUENCE_Level", allocationSize=25)
    protected Long id;	
	@NotNull
    protected String nom;
 
    public Level() {
    }
 
    public Level(String nom) {
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