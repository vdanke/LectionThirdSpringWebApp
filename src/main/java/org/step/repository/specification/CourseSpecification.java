package org.step.repository.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import org.step.model.Course;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

import static org.step.repository.specification.SpecificationUtil.wrapWithLikeLowerCase;

public class CourseSpecification implements Specification<Course> {

    private final CourseSearchingObject searchingObject;

    public CourseSpecification(CourseSearchingObject searchingObject) {
        this.searchingObject = searchingObject;
    }

    @Override
    public Predicate toPredicate(Root<Course> root,
                                 CriteriaQuery<?> criteriaQuery,
                                 CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicateList = new ArrayList<>();

        if (searchingObject == null) {
            return criteriaBuilder.and(predicateList.toArray(new Predicate[]{}));
        }

        if (!StringUtils.isEmpty(searchingObject.getName())) {
            predicateList.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(
                                    root.get("name")), wrapWithLikeLowerCase(searchingObject.getName())
                            )
                    );
        }
        if (!StringUtils.isEmpty(searchingObject.getDescription())) {
            predicateList.add(
                    criteriaBuilder.like(
                            criteriaBuilder.lower(
                                    root.get("description")), wrapWithLikeLowerCase(searchingObject.getDescription())
                            )
                    );
        }
        return criteriaBuilder.and(predicateList.toArray(new Predicate[]{}));
    }

    public CourseSearchingObject getSearchingObject() {
        return searchingObject;
    }
}
