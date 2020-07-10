package org.step.model;

import org.step.service.constraints.EmailConstraint;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.Email;

@MappedSuperclass
public abstract class Person {

    @EmailConstraint(message = "This email is not valid")
    @Column(name = "username", length = 120, unique = true)
    protected String username;
}
