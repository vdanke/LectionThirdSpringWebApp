package org.step.model.dto;

import java.time.LocalDateTime;

public class UserDto {

    private String username;
    private String fullName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public UserDto() {
    }

    public UserDto(String username, String fullName, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.username = username;
        this.fullName = fullName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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
}
