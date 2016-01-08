package com.example;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity

public class Form{
	@OneToMany
	List<Question> form;
	String nameForm;
	Long idCourse;
	@Id @GeneratedValue
	Long id;
	
	public Long getIdCourse() {
		return idCourse;
	}


	public void setIdCourse(Long idCourse) {
		this.idCourse = idCourse;
	}

	
	
	Form(){
		form= new ArrayList<Question>(); 
	}
	
	
	public String getNameForm() {
		return nameForm;
	}


	public void setNameForm(String name) {
		this.nameForm = name;
	}


	public Object findAll() {
		return this.getForm() ;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<Question> getForm() {
		return form;
	}
	public void setForm(ArrayList<Question> form) {
		this.form = form;
	}
}
