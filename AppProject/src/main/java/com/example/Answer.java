package com.example;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Answer {
	String answer;
	Boolean value;
	@Id
	@GeneratedValue
	Long id;

	Answer() {

	}

	public String getAnswer() {
		return answer;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
