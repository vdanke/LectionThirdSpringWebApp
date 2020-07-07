package org.step.repository;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.step.configuration.DatabaseConfiguration;
import org.step.model.User;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static util.UserData.USER_LIST;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class})
@Transactional
public class UserRepositoryIT {

    @PersistenceContext
    private EntityManager entityManager;

//    @Before
//    public void setup() {
//        entityManager.persist(USER_LIST.get(0));
//        entityManager.persist(USER_LIST.get(1));
//        entityManager.persist(USER_LIST.get(2));
//    }

    @Test
    public void userListShouldNotBeEmpty() {
        List<User> users = entityManager.createQuery("from User u", User.class)
                .getResultList();

        Assert.assertFalse(users.isEmpty());
        Assert.assertTrue(users.contains(USER_LIST.get(0)));
    }

    @Test
    public void shouldReturnUserByIdWithEntityGraph() {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("user.comments");

        Map<String, Object> map = new HashMap<>();

        map.put("javax.persistence.loadgraph", entityGraph);

        User singleResult = entityManager.find(User.class, 1, map);
    }

    @Test
    public void shouldReturnUserByIdWithCommentsList() {
        User user = entityManager.createQuery("select u from User u join fetch u.commentList where u.id=:id", User.class)
                .setParameter("id", 1)
                .getSingleResult();
    }

    @Test
    public void shouldReturnUserByProfileId() {
        User user = entityManager.createQuery("select u from User u inner join u.profile where u.profile.id=:id", User.class)
                .setParameter("id", 1L)
                .getSingleResult();
    }

    @Test
    public void leftJoinExample() {
        Object[] ids = entityManager.createQuery("select u, p from User u left join Profile p on u.id=p.user.id where u.id=:id", Object[].class)
                .setParameter("id", 1)
                .getSingleResult();

        System.out.println(ids[0].toString() + ids[1].toString());
    }

    @Test
    public void findUserById() {
        User user = entityManager.find(User.class, 1);

        Assert.assertNotNull(user);
        Assert.assertEquals(user.getId().toString(), "1");
    }

//    @After
//    public void clean() {
//        entityManager.remove(USER_LIST.get(0));
//        entityManager.remove(USER_LIST.get(1));
//        entityManager.remove(USER_LIST.get(2));
//    }
}
