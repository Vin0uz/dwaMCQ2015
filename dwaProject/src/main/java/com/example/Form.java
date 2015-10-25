package com.example;

import java.util.ArrayList;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity

public class Form {
	ArrayList<Question> form;
	@Id @GeneratedValue
	Long id;
	
	
	Form(){
		form= new ArrayList<Question>(); 
		Question v1= new Question();
		v1.setStringQuestion("Pile ou Face ?");
		form.add(v1);
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
