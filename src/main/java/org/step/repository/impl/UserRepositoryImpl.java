package org.step.repository.impl;

import org.springframework.stereotype.Repository;
import org.step.model.User;
import org.step.repository.UserRepository;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;
    private final User user = new User("first", "first", "first", 20);

    @PostConstruct
    public void init() throws SQLException {
        System.out.println("Init method");
    }

    @PreDestroy
    public void destroy() throws SQLException {
        System.out.println("Destroy method");
    }

    @Override
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public void delete(User user) {
        entityManager.remove(user);
    }

    @Override
    public List<User> findAllUsersAfterInsert(User user) {
        return entityManager.createQuery("select u from User u", User.class)
                .getResultList();
    }

    @Override
    public List<User> findAllUsers() {
        return entityManager.createQuery("select u from User u", User.class)
                .getResultList();
    }

    @Override
    public Optional<User> findUserById(Integer id) {
        return Optional.ofNullable(entityManager.find(User.class, id));
    }

    @Override
    public Optional<User> login(String username, String password) {
        return Optional.ofNullable(entityManager.createQuery("select u from User u where u.username=:username and u.password=:password", User.class)
                .getSingleResult());
    }
}
