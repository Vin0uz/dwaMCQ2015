package com.example;

import java.io.Serializable;
import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity

public class Form implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ArrayList<Question> form;
	String nameForm;
	Long idCourse;
	
	
	public Long getIdCourse() {
		return idCourse;
	}


	public void setIdCourse(Long idCourse) {
		this.idCourse = idCourse;
	}
	@Id @GeneratedValue
	Long id;
	
	
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
	public ArrayList<Question> getForm() {
		return form;
	}
	public void setForm(ArrayList<Question> form) {
		this.form = form;
	}
}
