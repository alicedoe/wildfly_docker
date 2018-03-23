package com.alicegabbana.restserver.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
public class Matiere {
	
	@Id
	@GeneratedValue (strategy= GenerationType.SEQUENCE, generator="SEQUENCE_Matiere")
	@SequenceGenerator(name = "SEQUENCE_Matiere", sequenceName = "SEQUENCE_Matiere", allocationSize=25)
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
		return "Matiere [id=" + id + ", nom=" + nom + "]";
	}
}