package org.step.model.projections;

public class BasedClassProjection {

    private final String username, fullName;

    public BasedClassProjection(String username, String fullName) {
        this.username = username;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getFullName() {
        return fullName;
    }
}
