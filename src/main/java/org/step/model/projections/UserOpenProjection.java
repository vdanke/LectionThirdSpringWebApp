package org.step.model.projections;

import org.springframework.beans.factory.annotation.Value;

public interface UserOpenProjection {

    @Value("#{target.username + ' ' + target.fullName}")
    String getUsernameFullName();
}
