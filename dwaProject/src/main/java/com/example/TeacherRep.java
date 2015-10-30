package com.example;

import java.util.ArrayList;

public interface TeacherRep {
	ArrayList<Course> findAll();
	void add(Course course);
}
