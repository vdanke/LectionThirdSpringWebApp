package org.step.service;

import org.step.model.Course;

import java.util.List;

public interface CourseService {

    List<Course> findAll();

    Course findById(String id);
}
