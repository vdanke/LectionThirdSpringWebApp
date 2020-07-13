package org.step.repository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.step.configuration.db.DatabaseConfiguration;
import org.step.model.Course;
import org.step.repository.specification.CourseSearchingObject;
import org.step.repository.specification.CourseSpecification;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class})
@Transactional
@ActiveProfiles("production")
public class CourseRepositoryIT {

    private CourseRepositorySpringData courseRepositorySpringData;
    private List<Long> ids = new ArrayList<>();

    @Before
    public void setup() {
        courseRepositorySpringData.saveAll(
                Arrays.asList(
                        new Course("first", "good"),
                        new Course("second", "nice"),
                        new Course("third", "bad")
                )
        ).forEach(course -> ids.add(course.getId()));
    }

    @Test
    public void shouldReturnAllCourses() {
        CourseSearchingObject searchingObject = new CourseSearchingObject(null, null);

        CourseSpecification specification = new CourseSpecification(searchingObject);

        List<Course> all = courseRepositorySpringData.findAll(specification);

        all.forEach(course -> System.out.println(course.getDescription()));
    }

    @Test
    public void shouldReturnAllCoursesByExample() {
        ExampleMatcher exampleMatcher = ExampleMatcher
                .matchingAll()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.endsWith())
                .withMatcher("description", contains().ignoreCase());

        Course course = new Course(null, "oo");

        Example<Course> courseExample = Example.of(course, exampleMatcher);

        List<Course> all = courseRepositorySpringData.findAll(courseExample);

        all.forEach(c -> System.out.println(c.getName() + " " + c.getDescription()));
    }

    @After
    public void clean() {
        ids.clear();
        courseRepositorySpringData.deleteAll();
    }

    @Autowired
    public void setCourseRepositorySpringData(CourseRepositorySpringData courseRepositorySpringData) {
        this.courseRepositorySpringData = courseRepositorySpringData;
    }
}
