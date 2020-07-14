package org.step.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.step.configuration.security.UserDetailsImpl;
import org.step.configuration.security.token.TokenProvider;
import org.step.model.User;
import org.step.model.dto.UserDto;
import org.step.model.dto.request.LoginRequest;
import org.step.model.dto.request.RegistrationUserRequest;
import org.step.model.dto.response.RegistrationUserResponse;
import org.step.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final TokenProvider tokenProvider;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          ModelMapper modelMapper,
                          UserService userService,
                          TokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
//        final String tokenPrefix = "Bearer ";

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        if (authenticate == null) {
            throw new RuntimeException();
        }
        SecurityContextHolder.getContext().setAuthentication(authenticate);

        UserDetailsImpl principal = (UserDetailsImpl) authenticate.getPrincipal();

        final String token = tokenProvider.generateToken(authenticate);

        final String authorization = "Authorization";

        return ResponseEntity
                .status(HttpStatus.OK)
                .header(authorization, token)
                .body(modelMapper.map(principal.getUser(), UserDto.class));
    }

    @PostMapping("/registration")
    public RegistrationUserResponse saveUser(@Valid @RequestBody RegistrationUserRequest request) {
        User user = new User(request.getFullName(), request.getUsername(), request.getPassword(), request.getAge());

        User afterSaving = userService.save(user);

        return modelMapper.map(afterSaving, RegistrationUserResponse.class);
    }
}
