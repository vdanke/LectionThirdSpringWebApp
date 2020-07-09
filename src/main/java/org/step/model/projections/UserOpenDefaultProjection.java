package org.step.model.projections;

public interface UserOpenDefaultProjection {

    String getUsername();
    String getFullName();

    default String getUsernameFullName() {
        return getUsername() + " " + getFullName();
    }
}
