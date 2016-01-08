package com.example;

import javax.inject.Inject;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyRestController {

	@Inject
	TeacherRepository courseRep;
	@Inject
	CourseRepository formRep ;
	@Inject
	FormRepository questionRep ; // return the list of questions contained in ONE form

	@RequestMapping(value="/aaaaform/{id}",method=RequestMethod.GET)
	Form myGetQuestions( @PathVariable("id") Long id){// return the form with id "FormId"
		return formRep.findOne(id);
	}
	
	@RequestMapping(value="/aaaaform",method=RequestMethod.GET)
	Iterable<Form> myGetForms(){
		return formRep.findAll();
	}
}
