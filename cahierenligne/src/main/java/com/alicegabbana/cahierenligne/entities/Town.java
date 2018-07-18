package com.alicegabbana.cahierenligne.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;

@Entity
public class Town implements Serializable {

	private static final long serialVersionUID = 8484870047345190268L;
	
	@Id
	@GeneratedValue (strategy= GenerationType.SEQUENCE, generator="SEQUENCE_Ville")
	@SequenceGenerator(name = "SEQUENCE_Ville", sequenceName = "SEQUENCE_Ville", allocationSize=25)
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
		return "Ville [id=" + id + ", name=" + name + "]";
	}
}