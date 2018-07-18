package com.alicegabbana.cahierenligne.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Setting implements Serializable {
	
	private static final long serialVersionUID = 609054930976754370L;
	
	@Id
	@Column(unique = true)
	@NotNull
	private String name;	
	@NotNull
	private String param;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
}
