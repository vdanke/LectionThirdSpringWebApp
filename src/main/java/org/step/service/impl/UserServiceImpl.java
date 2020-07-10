package org.step.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.step.exception.NotFoundException;
import org.step.model.User;
import org.step.repository.UserRepository;
import org.step.repository.UserRepositorySpringData;
import org.step.service.UserService;

import java.util.List;

/*
@Service
@Repository
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepositorySpringData userRepositorySpringData;

    @Autowired
    public UserServiceImpl(UserRepositorySpringData userRepositorySpringData) {
        this.userRepositorySpringData = userRepositorySpringData;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public User save(User user) {
        return userRepositorySpringData.save(user);
    }

    @Override
    @Transactional
    public void delete(User user) {
        userRepositorySpringData.delete(user);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepositorySpringData.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findUserById(Integer id) {
        return userRepositorySpringData.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "User with ID %d not found", id
                )));
    }

    @Override
    @Transactional(readOnly = true)
    public User login(String username, String password) {
        return userRepositorySpringData.findByUsernameAndPassword(username, password)
                .orElseThrow(() -> new NotFoundException(String.format(
                        "Login %s or password is incorrect", username
                )));
    }
}
