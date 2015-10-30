package com.example;

import java.util.ArrayList;
import java.util.Iterator;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MyController {
	@Inject
	FormRepository form;
	@Inject
	CourseRepository course;
	@Inject
	TeacherRepository teacher;

	Teacher currentTeacher;
	Course currentCourse;
	Form currentForm;
	@RequestMapping(value = "/")
	String Mapping(Model m) {
		m.addAttribute("teacher", new Teacher());
		m.addAttribute("courseList", teacher.findAll());
		m.addAttribute("course", new Course());
		m.addAttribute("formList", course.findAll());
		m.addAttribute("form", new Form());
		m.addAttribute("questionsList", form.findAll());
		m.addAttribute("question", new Question());

		return ("addcourses");
	}

	@RequestMapping(value = "/courses")
	String controllerCourses(Model m) {
		m.addAttribute("form", form.findAll());
		m.addAttribute("q", new Question());
		m.addAttribute("a0", new Answer());
		m.addAttribute("a1", new Answer());
		m.addAttribute("a2", new Answer());
		m.addAttribute("a3", new Answer());
		return ("addquestions");
	}

	@RequestMapping(value = "/addcourse", method = RequestMethod.POST)
	String controllerAddCourse(Course course) {
		teacher.save(course);
		return ("redirect:/test");
	}

	@RequestMapping(value = "/addquestion", method = RequestMethod.POST)
	String addQuestion(Question q, Answer a0) {
		boolean boolValue1 = false;
		boolean boolValue2 = false;
		boolean boolValue3 = false;
		boolean boolValue4 = false;
		q.setIdForm(currentForm.getId());
		Answer a1, a2, a3;
		String answer1, answer2, answer3 = null, answer4 = null;
		int coma1, coma2, coma3 = 0;
		int size = a0.answer.length();
		coma1 = a0.answer.indexOf(",");
		answer1 = a0.answer.substring(0, coma1);
		coma1++;
		coma2 = a0.answer.indexOf(",", coma1);
		String[] arrayValues = a0.listOfValues.split(",");
		for (int i = 0; i < arrayValues.length; i++) {
			if (arrayValues[i].compareTo("true1") == 0) {
				boolValue1 = true;
			}
			if (arrayValues[i].compareTo("true2") == 0) {
				boolValue2 = true;
			}
			if (arrayValues[i].compareTo("true3") == 0) {
				boolValue3 = true;
			}
			if (arrayValues[i].compareTo("true4") == 0) {
				boolValue4 = true;
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

	@RequestMapping(value = "/test")
	String controllerTest(Model m) {
		m.addAttribute("courseList", teacher.findAll());
		m.addAttribute("course", new Course());
		return ("index");
	}

	
	
	@RequestMapping(value = "/chooseCourse", method = RequestMethod.GET)
	String controllerChooseCourse(Model m, @RequestParam("id") Long id) {
		currentCourse= findCourseById(teacher.findAll(),id);
		m.addAttribute("course", currentCourse);
		m.addAttribute("formList", findFormsByCourseId(id));
		m.addAttribute("form", new Form());
		return ("addforms");
	}

	@RequestMapping(value = "/chooseForm", method = RequestMethod.GET)
	String controllerChooseForm(Model m, @RequestParam("id") Long id) {
		currentForm = course.findOne(id);
		m.addAttribute("q", new Question());
		m.addAttribute("a0", new Answer());
		m.addAttribute("a1", new Answer());
		m.addAttribute("a2", new Answer());
		m.addAttribute("a3", new Answer());
		m.addAttribute("form", currentForm);
		m.addAttribute("questionList", findQuestionsByCourseId(id));
		return ("addquestions");
	}
	private Object findQuestionsByCourseId(Long id) {
		Iterable<Question>  c = form.findAll();
		Iterator<Question> it = c.iterator();
		Question q;
		ArrayList<Question> formList = new ArrayList<Question>();
		while(it.hasNext()){
			q=it.next();
			if(q.getIdForm().equals(currentCourse.getId())){
				formList.add(q);
			}
		}
		return formList;	}

	private Course findCourseById(Iterable<Course> arrayCourse, Long id ) {
		Iterator<Course> it = arrayCourse.iterator();
		Course c;
		while(it.hasNext()){
		  c = it.next();
		  if(c.getId().equals(id)){
			  return c;
		  }
		}
		return null;
	}
	
	private ArrayList<Form> findFormsByCourseId(Long id){
		Iterable<Form>  c = course.findAll();
		Iterator<Form> it = c.iterator();
		Form f;
		ArrayList<Form> formList = new ArrayList<Form>();
		while(it.hasNext()){
			f=it.next();
			if(f.getIdCourse().equals(currentCourse.getId())){
				formList.add(f);
			}
		}
		return formList;
	}
	
	@RequestMapping(value = "/addform", method = RequestMethod.POST)
	String controllerAddForm(Model m, Form form) {
		System.out.println(form.nameForm +" aaa " + form.id);
		form.setIdCourse(currentCourse.getId());
		course.save(form);
		Iterable<Form>  c = course.findAll();
		Iterator<Form> it = c.iterator();
		Form f;
		ArrayList<Form> formList = new ArrayList<Form>();
		while(it.hasNext()){
			f=it.next();
			if(f.getIdCourse().equals(currentCourse.getId())){
				formList.add(f);
			}
		}
		m.addAttribute("formList", formList);
		return ("addforms");
	}

}
