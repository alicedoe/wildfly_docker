package com.alicegabbana.restserver.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Action implements Serializable {
	private static final long serialVersionUID = -4254732460564208575L;
	
	@Id
	@Column(unique = true)
	@NotNull
	private String name;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Action [name=" + name + "]";
	}
}
