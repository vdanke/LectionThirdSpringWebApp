package org.step.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.step.model.User;
import org.step.model.dto.request.RegistrationUserRequest;
import org.step.model.dto.response.RegistrationUserResponse;
import org.step.service.UserService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAllUsers() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public User findUserById(@PathVariable(name = "id") String id) {
        int userId = Integer.parseInt(id);
        return userService.findUserById(userId);
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable(name = "id") String id) {
        return null;
    }

    @PostMapping
    public RegistrationUserResponse saveUser(@Valid @RequestBody RegistrationUserRequest request) {
        User user = new User(request.getFullName(), request.getUsername(), request.getPassword(), request.getAge());

        User afterSaving = userService.save(user);

        return new RegistrationUserResponse(afterSaving.getUsername() , afterSaving.getFullName(), afterSaving.getAge());
    }
}
