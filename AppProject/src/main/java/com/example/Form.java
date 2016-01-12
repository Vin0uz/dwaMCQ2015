package com.example;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Form {
	String formName;
	@OneToMany(cascade = { CascadeType.ALL })
	List<Question> questions;
	@OneToMany(cascade = { CascadeType.ALL })
	List<Login> loginsDoneMCQ;

	@Id
	@GeneratedValue
	Long id;
	boolean online;

	Form() {
		questions = new ArrayList<Question>();
		loginsDoneMCQ = new ArrayList<Login>();
		online = false;
	}

	public List<Login> getLoginsDoneMCQ() {
		return loginsDoneMCQ;
	}

	public void setLoginsDoneMCQ(List<Login> loginsDoneMCQ) {
		this.loginsDoneMCQ = loginsDoneMCQ;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setQuestions(List<Question> answers) {
		this.questions = answers;
	}

}
