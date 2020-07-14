package org.step.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.step.configuration.security.UserDetailsImpl;
import org.step.model.User;
import org.step.model.dto.UserDto;
import org.step.model.dto.request.UpdateUserRequest;
import org.step.service.UserService;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RolesAllowed(value = {"ROLE_ADMIN", "ROLE_USER"})
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @Secured(value = {"ROLE_ADMIN"})
    public List<UserDto> findAllUsers() {
        return userService.findAllUsers()
                .stream()
                .map(user -> modelMapper.map(user, UserDto.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDto findUserById(@PathVariable(name = "id") String id) {
        int userId = Integer.parseInt(id);
        User userById = userService.findUserById(userId);
        return modelMapper.map(userById, UserDto.class);
    }

    @PutMapping("/{id}")
    @PreAuthorize("principal.user.id.toString.equals(#id)")
    public UserDto updateUser(@PathVariable(name = "id") String id,
                              @RequestBody UpdateUserRequest request) {
        User userById = userService.findUserById(Integer.parseInt(id));

        userById.setUsername(request.getUsername());
        userById.setFullName(request.getFullName());

        User afterSaving = userService.save(userById);
        return modelMapper.map(afterSaving, UserDto.class);
    }

    @GetMapping("/cabinet")
    @PreAuthorize("principal.user.id != null")
    public User getFullUserInformation(@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        return userDetails.getUser();
    }
}
