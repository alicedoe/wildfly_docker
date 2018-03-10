package com.alicegabbana.resterver.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Niveau {
	
	@Id @GeneratedValue
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
}