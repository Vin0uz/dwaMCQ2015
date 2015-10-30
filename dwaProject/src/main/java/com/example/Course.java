package com.example;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Course {
	String name;
	ArrayList<Form> forms;
	@Id @GeneratedValue
	Long id;

	Course(){
		forms= new ArrayList<Form>();
		name="";
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<Form> getForms() {
		return forms;
	}
	public void setForms(ArrayList<Form> forms) {
		this.forms = forms;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Object findAll() {
		return forms;
	}
	

}
