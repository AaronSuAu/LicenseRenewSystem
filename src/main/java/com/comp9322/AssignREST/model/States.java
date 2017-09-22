package com.comp9322.AssignREST.model;

import org.springframework.stereotype.Component;

@Component
public class States {
	private int id;
	private String name;
	private String abbrev;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAbbrev() {
		return abbrev;
	}
	public void setAbbrev(String abbrev) {
		this.abbrev = abbrev;
	}
	
}
