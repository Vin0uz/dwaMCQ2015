package com.example;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Answer implements Serializable {
	private static final long serialVersionUID = 7731111547282442873L;
	String answer;
	String listOfValues;
	boolean value;
	@Id @GeneratedValue
	Long id;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	Answer(){
		answer="";
		
	}
	public String getAnswer() {
		return answer;
	}
	public String getListOfValues() {
		return listOfValues;
	}
	public void setListOfValues(String listofValues) {
		this.listOfValues = listofValues;
	}
	public void setValue(boolean value) {
		this.value = value;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Boolean getValue() {
		return value;
	}
	public void setValue(Boolean value) {
		this.value = value;
	}
}
