package org.step.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.step.model.Course;
import org.step.repository.CourseRepositorySpringData;
import org.step.service.CourseService;

import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepositorySpringData courseRepositorySpringData;

    @Autowired
    public CourseServiceImpl(CourseRepositorySpringData courseRepositorySpringData) {
        this.courseRepositorySpringData = courseRepositorySpringData;
    }

    @Override
    public List<Course> findAll() {
        return courseRepositorySpringData.findAll();
    }

    @Override
    public Course findById(String id) {
        return courseRepositorySpringData.findById(Long.parseLong(id))
                .orElseThrow(RuntimeException::new);
    }
}
