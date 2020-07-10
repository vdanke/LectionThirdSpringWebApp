package org.step.model.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class RegistrationUserRequest {

    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String fullName;
    @Min(value = 18)
    private Integer age;

    public RegistrationUserRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
