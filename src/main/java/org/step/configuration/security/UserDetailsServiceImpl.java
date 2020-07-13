package org.step.configuration.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.step.model.User;
import org.step.repository.UserRepositorySpringData;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepositorySpringData userRepositorySpringData;

    @Autowired
    public UserDetailsServiceImpl(UserRepositorySpringData userRepositorySpringData) {
        this.userRepositorySpringData = userRepositorySpringData;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepositorySpringData.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with %s username not found", username))
                );
        return new UserDetailsImpl(user);
    }
}
