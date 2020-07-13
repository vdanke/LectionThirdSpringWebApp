package org.step.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.step.model.User;
import org.step.model.projections.UserOpenProjection;
import org.step.model.projections.UserProjection;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

//@Repository
public interface UserRepositorySpringData extends AbstractUserRepository<User> {

    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);

    @Query("select u from User u where u.username like %?1%")
    List<User> findAllByUsernameLike(String username);

    @Modifying
    @Query(value="DELETE FROM USERS WHERE ID = ?", nativeQuery=true)
    void deleteUserById(Integer id);

    @Query("select u from User u where u.username=:username or u.fullName=:fullName")
    List<User> findByUsernameOrFullName(@Param("username") String username,
                                        @Param("fullName") String fullName);

    @Query("select u from User u where u.username=:#{principal.username}")
    Optional<User> findByPrincipal();

    @EntityGraph(value = "user.comments", type = EntityGraph.EntityGraphType.LOAD)
    @Query("select u from User u where u.id=:id")
    Optional<User> findByIdWithEntityGraph(@Param("id") Integer id);

    @EntityGraph(attributePaths = {"commentList"}, type = EntityGraph.EntityGraphType.FETCH)
    @Query("select u from User u where u.id=:id")
    Optional<User> findByIdWithEntityGraphFetch(@Param("id") Integer id);

    List<UserProjection> findAllByFullNameContains(String fullName);

    @Query("select u from User u")
    List<UserOpenProjection> findAllWithOpenProjection();

    @Query("select u from User u")
    <T> Collection<T> findAllWithDifferentProjection(Class<T> tClass);
}
