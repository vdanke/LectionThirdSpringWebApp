package org.step.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.step.configuration.db.DatabaseConfiguration;
import org.step.model.User;
import org.step.model.projections.UserOpenProjection;
import org.step.model.projections.UserProjection;
import org.step.repository.specification.UserSpecification;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static util.UserData.USER_LIST;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class})
@Transactional
@ActiveProfiles("production")
public class UserRepositoryIT {

    private UserRepositorySpringData userRepositorySpringData;
    private List<Integer> ids = new ArrayList<>();

    @Before
    public void setup() {
        userRepositorySpringData.saveAll(USER_LIST)
                .forEach(u -> {
                    ids.add(u.getId());
                });
    }

    @Test
    public void shouldReturnAllUsers() {
        List<User> users = userRepositorySpringData.findAll();

        users.forEach(user -> System.out.println(user.getUpdatedAt()));

        Assert.assertFalse(users.isEmpty());
        Assert.assertEquals(3, users.size());
    }

    @Test
    public void shouldReturnUserById() {
        Integer id = ids.get(0);

        Optional<User> userById = userRepositorySpringData.findById(id);

        Assert.assertTrue(userById.isPresent());
    }

    @Test
    public void shouldFindByUsernameAndPassword() {
        User user = USER_LIST.get(0);

        final String username = user.getUsername();
        final String password = user.getPassword();

        Optional<User> optionalUser = userRepositorySpringData.findByUsernameAndPassword(username, password);

        Assert.assertTrue(optionalUser.isPresent());
    }

    @Test
    public void shouldReturnAllUsersByUsernameLike() {
        final int expectedListSize = 2;

        List<User> allByUsernameLike = userRepositorySpringData.findAllByUsernameLike("ir");

        long irContainsUsers = allByUsernameLike
                .stream()
                .filter(u -> u.getUsername().contains("ir"))
                .count();

        Assert.assertFalse(allByUsernameLike.isEmpty());
        Assert.assertEquals(expectedListSize, allByUsernameLike.size());
        Assert.assertEquals(expectedListSize, irContainsUsers);
    }

    @Test
    public void shouldDeleteUserById() {
        Integer id = ids.get(0);

        userRepositorySpringData.deleteUserById(id);

        boolean isMatch = userRepositorySpringData
                .findAll()
                .stream()
                .anyMatch(u -> u.getId().equals(id));

        Assert.assertFalse(isMatch);
    }

    @Test
    public void shouldReturnListOfUsersQueriedBySpEL() {
        final int expectedListSize = 2;

        List<User> allByUsernameLike = userRepositorySpringData.findByUsernameLikeWithSpEL("ir");

        long irContainsUsers = allByUsernameLike
                .stream()
                .filter(u -> u.getUsername().contains("ir"))
                .count();

        Assert.assertFalse(allByUsernameLike.isEmpty());
        Assert.assertEquals(expectedListSize, allByUsernameLike.size());
        Assert.assertEquals(expectedListSize, irContainsUsers);
    }

    @Test
    public void shouldReturnUserByIdWithEntityGraph() {
        Optional<User> byIdWithEntityGraph = userRepositorySpringData.findByIdWithEntityGraph(ids.get(0));

        Assert.assertTrue(byIdWithEntityGraph.isPresent());
    }

    @Test
    public void shouldReturnUserByIdWithEntityGraphFetch() {
        Optional<User> byIdWithEntityGraphFetch = userRepositorySpringData.findByIdWithEntityGraphFetch(ids.get(0));

        Assert.assertTrue(byIdWithEntityGraphFetch.isPresent());
        Assert.assertNotNull(byIdWithEntityGraphFetch.get().getCommentList());
    }

    @Test
    public void shouldReturnUserProjection() {
        List<UserProjection> nameContains = userRepositorySpringData.findAllByFullNameContains("ir");

        nameContains.forEach(p -> System.out.println(p.getUsername()));
    }

    @Test
    public void shouldReturnOpenProjection() {
        List<UserOpenProjection> allWithOpenProjection = userRepositorySpringData.findAllWithOpenProjection();

        allWithOpenProjection.forEach(u -> System.out.println(u.getUsernameFullName()));
    }

    @Test
    public void shouldReturnWithDifferentProjection() {
        Collection<UserOpenProjection> allWithDifferentProjection = userRepositorySpringData.findAllWithDifferentProjection(UserOpenProjection.class);

        allWithDifferentProjection.forEach(u -> System.out.println(u.getUsernameFullName()));
    }

    @Test
    public void shouldReturnByUsernameLike() {
        UserSpecification specification = new UserSpecification("irs");

        List<User> all = userRepositorySpringData.findAll(specification);

        all.forEach(u -> System.out.println(u.getUsername()));
    }

    @After
    public void clean() {
        ids.clear();
        userRepositorySpringData.deleteAll();
    }

    @Autowired
    public void setUserRepositorySpringData(UserRepositorySpringData userRepositorySpringData) {
        this.userRepositorySpringData = userRepositorySpringData;
    }
}
