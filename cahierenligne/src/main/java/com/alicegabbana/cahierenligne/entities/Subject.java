package com.alicegabbana.cahierenligne.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
public class Subject implements Serializable {

	private static final long serialVersionUID = -3213632519730100265L;
	
	@Id
	@GeneratedValue (strategy= GenerationType.SEQUENCE, generator="SEQUENCE_Subject")
	@SequenceGenerator(name = "SEQUENCE_Subject", sequenceName = "SEQUENCE_Subject", allocationSize=25)
	private Long id;	
	@NotNull
	private String name;
 
    public Long getId() {
        return id;
    }
 
    public void setId(Long id) {
        this.id = id;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }

	@Override
	public String toString() {
		return "Subject [id=" + id + ", name=" + name + "]";
	}
}