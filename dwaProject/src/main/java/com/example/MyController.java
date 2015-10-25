package com.example;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MyController {
	@Inject
	FormRepository form;

	@RequestMapping(value = "/")
	String Mapping(Model m) {
		m.addAttribute("form", form.findAll());
		m.addAttribute("q", new Question());
		m.addAttribute("a0", new Answer());
		m.addAttribute("a1", new Answer());
		m.addAttribute("a2", new Answer());
		m.addAttribute("a3", new Answer());
		return ("index");
	}

	/*
	 * @RequestMapping(value="/addquestion",method=RequestMethod.POST) String
	 * addQuestion(Question q, Answer a0, Answer a1){ q.answers.add(a0);
	 * q.answers.add(a1); form.save(q); return ("redirect:/"); }
	 * 
	 * @RequestMapping(value="/addquestion",method=RequestMethod.POST) String
	 * addQuestion(Question q, Answer a0, Answer a1, Answer a2){
	 * q.answers.add(a0); q.answers.add(a1); q.answers.add(a2); form.save(q);
	 * return ("redirect:/"); }
	 */

	@RequestMapping(value = "/addquestion", method = RequestMethod.POST)
	String addQuestion(Question q, Answer a0) {
		System.out.println(a0.listOfValues + "AAAAAAAAAAAHH ----------------------");
		boolean boolValue1 = false;
		boolean boolValue2 = false;
		boolean boolValue3 = false;
		boolean boolValue4 = false;
		Answer a1, a2, a3;
		String answer1, answer2, answer3 = null, answer4 = null;
		int coma1, coma2, coma3 = 0;
		int size = a0.answer.length();
		coma1 = a0.answer.indexOf(",");
		answer1 = a0.answer.substring(0, coma1);
		coma1++;
		coma2 = a0.answer.indexOf(",", coma1);
		String[] arrayValues = a0.listOfValues.split(",");
		for(int i=0; i< arrayValues.length ; i++){
				if(arrayValues[i].compareTo("true1")==0){
					boolValue1=true;
				}
				if(arrayValues[i].compareTo("true2")==0){
					boolValue2=true;
				}
				if(arrayValues[i].compareTo("true3")==0){
					boolValue3=true;
				}
				if(arrayValues[i].compareTo("true4")==0){
					boolValue4=true;
				}
			}
		if (coma2 != -1) {
			answer2 = a0.answer.substring(coma1, coma2);
			coma2++;
			coma3 = a0.answer.indexOf(",", coma2);
			if (coma3 != -1) {
				answer3 = a0.answer.substring(coma2, coma3);
				coma3++;
				answer4 = a0.answer.substring(coma3, size);
			} else {
				answer3 = a0.answer.substring(coma2, size);
			}
		}

		else {
			answer2 = a0.answer.substring(coma1, size);

		}
		a0.answer = answer1;
		a0.value = boolValue1;
		q.answers.add(a0);
		if (coma1 != -1) {
			a1 = new Answer();
			a1.answer = answer2;
			a1.value = boolValue2;

			q.answers.add(a1);
		}
		if (coma2 > 0) {
			a2 = new Answer();
			a2.answer = answer3;
			a2.value = boolValue3;
			q.answers.add(a2);
		}
		if (coma3 > 0) {
			a3 = new Answer();
			a3.answer = answer4;
			a3.value = boolValue4;
			q.answers.add(a3);
		}
		form.save(q);
		return ("redirect:/");
	}

}
