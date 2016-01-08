package com.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;


@Entity
public class Question implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String stringQuestion;
	@OneToMany(cascade = {CascadeType.ALL})
	List<Answer> answers ;
	int numberAnswer;
	Long idForm;
	@Id @GeneratedValue
	Long questionId;

	
	public int getNumberAnswer() {
		return numberAnswer;
	}

	public void setNumberAnswer(int numberAnswer) {
		this.numberAnswer = numberAnswer;
	}
	public Long getIdForm() {
		return idForm;
	}

	public void setIdForm(Long idForm) {
		this.idForm = idForm;
	}

	Question(){
		answers= new ArrayList<Answer>();
		stringQuestion="";
	}

	public String getStringQuestion() {
		return stringQuestion;
	}
	public void setStringQuestion(String question) {
		this.stringQuestion = question;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long id) {
		this.questionId = id;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}
	
	
}
