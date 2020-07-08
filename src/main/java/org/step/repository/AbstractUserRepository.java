package org.step.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;
import org.step.model.Person;
import org.step.model.User;

import java.util.List;

@NoRepositoryBean
public interface AbstractUserRepository<T extends Person> extends JpaRepository<T, Integer> {

    @Query("select u from #{#entityName} u where u.username like %?1%")
    List<User> findByUsernameLikeWithSpEL(String username);
}
