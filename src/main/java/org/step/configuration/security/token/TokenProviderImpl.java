package org.step.configuration.security.token;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.step.configuration.security.UserDetailsImpl;

import java.util.Date;
import java.util.Objects;

public class TokenProviderImpl implements TokenProvider {

    private final Environment environment;

    public TokenProviderImpl(Environment environment) {
        this.environment = environment;
    }

    @Override
    public String generateToken(Authentication authentication) {
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();

        String id = principal.getUser().getId().toString();

        String tokenSecret = environment.getProperty("secret.token");
        long expirationTime = Long.parseLong(Objects.requireNonNull(environment.getProperty("expiration.time")));

        return Jwts.builder()
                .setSubject(id)
                .setExpiration(new Date(System.currentTimeMillis() + expirationTime))
                .signWith(SignatureAlgorithm.HS512, tokenSecret)
                .compact();
    }

    @Override
    public String validateToken(String token) {
        return Jwts.parser()
                .setSigningKey(environment.getProperty("secret.token"))
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
