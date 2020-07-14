package org.step.configuration.security.token;

import org.springframework.security.core.Authentication;

public interface TokenProvider {

    String generateToken(Authentication authentication);

    String validateToken(String token);
}
