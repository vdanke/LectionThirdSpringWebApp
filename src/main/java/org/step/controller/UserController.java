package org.step.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.step.model.User;
import org.step.model.dto.UserDto;
import org.step.model.dto.request.RegistrationUserRequest;
import org.step.model.dto.request.UpdateUserRequest;
import org.step.model.dto.response.RegistrationUserResponse;
import org.step.service.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    public List<UserDto> findAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public UserDto findUserById(@PathVariable(name = "id") String id) {
        int userId = Integer.parseInt(id);
        User userById = userService.findUserById(userId);
        return modelMapper.map(userById, UserDto.class);
    }

    @PutMapping("/{id}")
    public UserDto updateUser(@PathVariable(name = "id") String id,
                              @RequestBody UpdateUserRequest request) {
        User userById = userService.findUserById(Integer.parseInt(id));

        userById.setUsername(request.getUsername());
        userById.setFullName(request.getFullName());

        User afterSaving = userService.save(userById);
        return modelMapper.map(afterSaving, UserDto.class);
    }

    @PostMapping
    public RegistrationUserResponse saveUser(@Valid @RequestBody RegistrationUserRequest request) {
        User user = new User(request.getFullName(), request.getUsername(), request.getPassword(), request.getAge());

        User afterSaving = userService.save(user);

        return modelMapper.map(afterSaving, RegistrationUserResponse.class);
    }
}
