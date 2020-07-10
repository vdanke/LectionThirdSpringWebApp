package org.step.model.dto.response;

public class RegistrationUserResponse {

    private String username;
    private String fullName;
    private Integer age;

    public RegistrationUserResponse() {
    }

    public RegistrationUserResponse(String username, String fullName, Integer age) {
        this.username = username;
        this.fullName = fullName;
        this.age = age;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
