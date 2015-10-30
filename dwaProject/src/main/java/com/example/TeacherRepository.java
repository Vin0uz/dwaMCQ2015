package com.example;

import org.springframework.data.repository.CrudRepository;

public interface TeacherRepository extends CrudRepository<Course, Long> {

}
