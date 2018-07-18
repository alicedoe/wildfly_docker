package com.alicegabbana.cahierenligne.entities;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

@Entity
public class Role implements Serializable {
	private static final long serialVersionUID = -430263964220759796L;
	
	@Id	
	@NotNull
	private String name;	
	@ManyToMany(fetch= FetchType.EAGER)
	private List <Action> actions;

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Role [name=" + name + ", actions=" + actions + "]";
	}
	
}