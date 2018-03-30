package com.alicegabbana.restserver.dto;

import java.util.List;

public class RoleDto {

	private Long id;
	private String name;
	private List<Long> actions;
	
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
	public List<Long> getActions() {
		return actions;
	}
	public void setActions(List<Long> actions) {
		this.actions = actions;
	}
	@Override
	public String toString() {
		return "RoleDto [id=" + id + ", name=" + name + ", actions=" + actions + "]";
	}
	
	
}