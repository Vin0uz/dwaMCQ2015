package com.example;

import java.util.ArrayList;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class MyRestController {

	@Inject
	UserRepository users;

	@Inject
	OnlineFormsRepository onlineForms;

	@RequestMapping(value = "/onlineforms/{id}", method = RequestMethod.GET)
	public Form myGetOnlineFormById(@PathVariable("id") Long id) {
		return onlineForms.findOne(id);
	}

	@RequestMapping(value = "/onlineforms/{id}", method = RequestMethod.POST)
	public Form update(@PathVariable("id") long id, @RequestBody Form form) {
		onlineForms.save(form);
		return form;
	}

	@RequestMapping(value = "/onlineforms", method = RequestMethod.PUT)
	public Form updateForm(@RequestBody Form form) {
		onlineForms.save(form);
		return form;
	}

	@RequestMapping(value = "/onlineforms", method = RequestMethod.GET)
	public Iterable<Form> myGetOnlineForms() {
		ArrayList<Form> result = new ArrayList<Form>();
		for (Form f : onlineForms.findAll()) {
			if (f.isOnline()) {
				result.add(f);
			}
		}
		return result;
	}

	@RequestMapping(value = "/onlineforms", method = RequestMethod.DELETE)
	public ResponseEntity<Form> deleteOnlineForm(@RequestBody Form form) {
		onlineForms.delete(form.id);
		return new ResponseEntity<Form>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/onlineforms/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Form> deleteOnlineFormId(@PathVariable("id") long id) {
		onlineForms.delete(id);
		return new ResponseEntity<Form>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
	public User myGetUserById(@PathVariable("id") Long id) {
		return users.findOne(id);
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.POST)
	public User update(@PathVariable("id") long id, @RequestBody User user) {
		users.save(user);
		return user;
	}

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public Iterable<User> myGetUsers() {// return the form with id "FormId"
		return users.findAll();
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	public ResponseEntity<Void> createUser(@RequestBody User user) {

		users.save(user);

		return new ResponseEntity<Void>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/users", method = RequestMethod.PUT)
	public ResponseEntity<Void> updateUser(@RequestBody User user) {

		users.delete(user.id);
		users.save(user);

		return new ResponseEntity<Void>(HttpStatus.CREATED);

	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {

		User user = users.findOne(id);
		if (user == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		users.delete(id);
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/login/{id}", method = RequestMethod.GET)
	public Login myGetLogin(@PathVariable("id") Long id) {
		return (new Login());
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public Iterable<Login> myGetLogins() {//
		return (new ArrayList<Login>());
	}

	/* FORMS */
	@RequestMapping(value = "/forms/{id}", method = RequestMethod.GET)
	public Form myGetForm(@PathVariable("id") Long id) {
		return (new Form());
	}

	@RequestMapping(value = "/forms", method = RequestMethod.GET)
	public Iterable<Form> myGetForms() {
		return (new ArrayList<Form>());
	}

	/* QUESTIONS */
	@RequestMapping(value = "/questions/{id}", method = RequestMethod.GET)
	public Question myGetQuestions(@PathVariable("id") Long id) {
		return (new Question());
	}

	@RequestMapping(value = "/questions", method = RequestMethod.GET)
	public Iterable<Question> myGetQuestions() {// return the form with id
												// "FormId"
		return (new ArrayList<Question>());
	}

	/* ANSWERS */
	@RequestMapping(value = "/answers/{id}", method = RequestMethod.GET)
	public Answer myGetAnswers(@PathVariable("id") Long id) {
		return (new Answer());
	}

	@RequestMapping(value = "/answers", method = RequestMethod.GET)
	public Iterable<Answer> myGetAnswers() {// return the form with id "FormId"
		return (new ArrayList<Answer>());
	}

}
