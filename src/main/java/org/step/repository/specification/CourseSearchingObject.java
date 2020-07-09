package org.step.repository.specification;

public class CourseSearchingObject {

    private final String name, description;

    public CourseSearchingObject(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
