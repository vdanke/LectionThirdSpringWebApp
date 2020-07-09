package org.step.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.step.model.User;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static org.step.repository.specification.SpecificationUtil.wrapWithLikeLowerCase;

public class UserSpecification implements Specification<User> {

    private final String username;

    public UserSpecification(String username) {
        this.username = username;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(
                criteriaBuilder.lower(
                        root.get("username")), wrapWithLikeLowerCase(this.username)
        );
    }
}
