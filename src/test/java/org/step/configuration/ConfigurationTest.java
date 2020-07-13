package org.step.configuration;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.step.configuration.db.DatabaseConfiguration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DatabaseConfiguration.class})
public class ConfigurationTest {

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void entityManagerShouldNotBeNull() {
        Assert.assertNotNull(entityManager);
    }
}
