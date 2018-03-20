package com.alicegabbana.restserver.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Setting {
	
	@Id
	private String id;	
	@NotNull
	private String param;
	
	public Setting() {
	}	
	
	public Setting(String id, String param) {
		super();
		this.id = id;
		this.param = param;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getParam() {
		return param;
	}
	public void setParam(String param) {
		this.param = param;
	}
}
