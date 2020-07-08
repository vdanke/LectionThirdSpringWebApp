package org.step.repository;

import org.step.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    void delete(User user);

    List<User> findAllUsersAfterInsert(User user);

    List<User> findAllUsers();

    Optional<User> findUserById(Integer id);

    Optional<User> login(String username, String password);
}
