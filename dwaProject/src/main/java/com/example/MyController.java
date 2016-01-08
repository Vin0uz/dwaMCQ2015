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

	
	/*@RequestMapping(value="mcq")
	String mcqMapping(Model m){
		return("mcq");
	}
	*/
	@RequestMapping(value = "/accueil")
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

	@RequestMapping(value = "/addquestion", method = RequestMethod.POST, params = "action=choose")
	String addQuestion(Question question, Answer a0) {
		boolean boolValue1 = false;
		boolean boolValue2 = false;
		boolean boolValue3 = false;
		boolean boolValue4 = false;
		question.setIdForm(currentForm.getId());
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
		question.answers.add(a0);
		if (coma1 != -1) {
			a1 = new Answer();
			a1.answer = answer2;
			a1.value = boolValue2;

			question.answers.add(a1);
		}
		if (coma2 > 0) {
			a2 = new Answer();
			a2.answer = answer3;
			a2.value = boolValue3;
			question.answers.add(a2);
		}
		if (coma3 > 0) {
			a3 = new Answer();
			a3.answer = answer4;
			a3.value = boolValue4;
			question.answers.add(a3);
		}
		course.findOne(currentForm.getId()).form.add(question);
		form.save(question);
		return ("redirect:/");
	}

	@RequestMapping(value = "/addquestion", method = RequestMethod.POST, params = "action=delete")
	String deleteQuestion(Model m, @RequestParam("id") Long id) {
		Long idForm = form.findOne(id).getIdForm();
		DeleteQuestionById(id);
		m.addAttribute("form", currentForm);
		m.addAttribute("questionList", findQuestionsByFormId(idForm));
		return ("addquestions");
	}

	@RequestMapping(value = "/test")
	String controllerTest(Model m) {
		m.addAttribute("courseList", teacher.findAll());
		m.addAttribute("course", new Course());
		return ("accueil");
	}

	@RequestMapping(value = "/chooseCourse", method = RequestMethod.GET, params = "action=choose")
	String controllerChooseCourse(Model m, @RequestParam("id") Long id) {
		currentCourse = findCourseById(teacher.findAll(), id);
		m.addAttribute("course", currentCourse);
		m.addAttribute("formList", findFormsByCourseId(id));
		m.addAttribute("form", new Form());
		return ("addforms");
	}

	@RequestMapping(value = "/chooseCourse", method = RequestMethod.GET, params = "action=delete")
	String controllerDeleteCourse(Model m, @RequestParam("id") Long id) {
		teacher.delete(id);
		ArrayList<Form> array = findFormsByCourseId(id);
		Iterator<Form> it = array.iterator();
		while (it.hasNext()) {
			DeleteFormById(it.next().getId());
		}
		m.addAttribute("courseList", teacher.findAll());
		m.addAttribute("course", new Course());
		return ("accueil");
	}

	private void DeleteFormById(Long id) {
		course.delete(id);
		ArrayList<Question> array = findQuestionsByFormId(id);
		Iterator<Question> it = array.iterator();
		while (it.hasNext()) {
			DeleteQuestionById(it.next().getQuestionId());
		}
	}

	private void DeleteQuestionById(Long id) {
		form.delete(id);
	}

	@RequestMapping(value = "/chooseForm", method = RequestMethod.GET, params = "action=choose")
	String controllerChooseForm(Model m, @RequestParam("id") Long id) {
		currentForm = course.findOne(id);
		m.addAttribute("question", new Question());
		m.addAttribute("a0", new Answer());
		m.addAttribute("a1", new Answer());
		m.addAttribute("a2", new Answer());
		m.addAttribute("a3", new Answer());
		m.addAttribute("form", currentForm);
		m.addAttribute("questionList", findQuestionsByFormId(id));
		return ("addquestions");
	}

	@RequestMapping(value = "/chooseForm", method = RequestMethod.GET, params = "action=delete")
	String controllerDeleteForm(Model m, @RequestParam("id") Long id) {
		Long courseId = course.findOne(id).getIdCourse();
		DeleteFormById(id);
		m.addAttribute("formList", findFormsByCourseId(courseId));
		m.addAttribute("form", new Form());
		return ("addforms");
	}

	private ArrayList<Question> findQuestionsByFormId(Long id) {
		Iterable<Question> c = form.findAll();
		Iterator<Question> it = c.iterator();
		Question q;
		ArrayList<Question> formList = new ArrayList<Question>();
		while (it.hasNext()) {
			q = it.next();
			if (q.getIdForm().equals(currentCourse.getId())) {
				formList.add(q);
			}
		}
		return formList;
	}

	private Course findCourseById(Iterable<Course> arrayCourse, Long id) {
		Iterator<Course> it = arrayCourse.iterator();
		Course c;
		while (it.hasNext()) {
			c = it.next();
			if (c.getId().equals(id)) {
				return c;
			}
		}
		return null;
	}

	private ArrayList<Form> findFormsByCourseId(Long id) {
		Iterable<Form> c = course.findAll();
		Iterator<Form> it = c.iterator();
		Form f;
		ArrayList<Form> formList = new ArrayList<Form>();
		while (it.hasNext()) {
			f = it.next();
			if (f.getIdCourse().equals(currentCourse.getId())) {
				formList.add(f);
			}
		}
		return formList;
	}

	@RequestMapping(value = "/addform", method = RequestMethod.POST)
	String controllerAddForm(Model m, Form form) {
		System.out.println(form.nameForm + " aaa " + form.id);
		form.setIdCourse(currentCourse.getId());
		course.save(form);
		Iterable<Form> c = course.findAll();
		Iterator<Form> it = c.iterator();
		Form f;
		ArrayList<Form> formList = new ArrayList<Form>();
		while (it.hasNext()) {
			f = it.next();
			if (f.getIdCourse().equals(currentCourse.getId())) {
				formList.add(f);
			}
		}
		m.addAttribute("formList", formList);
		return ("addforms");
	}

}
