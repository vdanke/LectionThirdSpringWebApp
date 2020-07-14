package org.step.configuration;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.step.model.Course;
import org.step.model.Role;
import org.step.model.User;
import org.step.repository.CourseRepositorySpringData;
import org.step.repository.UserRepositorySpringData;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class AppConfiguration {

    private final UserRepositorySpringData userRepository;
    private final CourseRepositorySpringData courseRepository;

    @Autowired
    public AppConfiguration(UserRepositorySpringData userRepository,
                            CourseRepositorySpringData courseRepository) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        return modelMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        final int strength = 8;

        return new BCryptPasswordEncoder(strength);
    }

    @EventListener(ContextRefreshedEvent.class)
    public void setup() {
        userRepository.deleteAll();

        User first = new User("first1", "first123@mail.ru", passwordEncoder().encode("first"), 19);
        User second = new User("second2", "second123@google.com", passwordEncoder().encode("second"), 30);
        User third = new User("third3", "third123@yandex.ru", passwordEncoder().encode("third"), 45);

        first.setAuthorities(Collections.singleton(Role.ROLE_USER));
        second.setAuthorities(Collections.singleton(Role.ROLE_USER));
        third.setAuthorities(Collections.singleton(Role.ROLE_ADMIN));

        userRepository.saveAll(Arrays.asList(first, second, third));

        Course fCourse = new Course("first", "first");
        Course sCourse = new Course("second", "second");
        Course tCourse = new Course("third", "third");

        courseRepository.saveAll(Arrays.asList(fCourse, sCourse, tCourse));
    }
}
