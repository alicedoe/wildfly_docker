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
	private Long id;	
	@NotNull
	private String name;
 
    public Level() {
    }
 
    public Level(String name) {
        this.name = name;
    }
 
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
		return "Niveau [id=" + id + ", name=" + name + "]";
	}
}