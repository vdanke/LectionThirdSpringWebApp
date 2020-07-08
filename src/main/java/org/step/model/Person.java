package org.step.model;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Person {

    @Column(name = "username", length = 120, unique = true)
    protected String username;
}
