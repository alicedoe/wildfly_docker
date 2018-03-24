package com.alicegabbana.restserver.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
public class Subject {
	
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