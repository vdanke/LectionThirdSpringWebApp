package org.step.model.projections;

public class OnlyUsernameClassProjection {

    private final String username;

    public OnlyUsernameClassProjection(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
