package org.step.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.step.model.Course;

public interface CourseRepositorySpringData extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
}
