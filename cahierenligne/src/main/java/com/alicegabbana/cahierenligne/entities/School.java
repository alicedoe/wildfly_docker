package com.alicegabbana.cahierenligne.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
public class School implements Serializable {
	
	private static final long serialVersionUID = -3700516815475302277L;
	
	@Id
	@GeneratedValue (strategy= GenerationType.SEQUENCE, generator="SEQUENCE_School")
	@SequenceGenerator(name = "SEQUENCE_School", sequenceName = "SEQUENCE_School", allocationSize=25)
    private Long id;
	@NotNull
	private String name;
	@NotNull
	@ManyToOne
	private Town town;
	
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
	public Town getTown() {
		return town;
	}
	public void setTown(Town town) {
		this.town = town;
	}
	@Override
	public String toString() {
		return "School [id=" + id + ", name=" + name + ", town=" + town + "]";
	}
}