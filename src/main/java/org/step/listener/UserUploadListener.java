package org.step.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.step.model.User;
import org.step.repository.UserRepositorySpringData;

import java.util.Arrays;

@Component
public class UserUploadListener implements ApplicationListener<ContextRefreshedEvent> {

    private final UserRepositorySpringData userRepository;

    @Autowired
    public UserUploadListener(UserRepositorySpringData userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        userRepository.deleteAll();

        User first = new User("first1", "first123@mail.ru", "first", 19);
        User second = new User("second2", "second123@google.com", "second", 30);
        User third = new User("third3", "third123@yandex.ru", "third", 45);

        userRepository.saveAll(Arrays.asList(first, second, third));
    }
}
