package util;

import org.step.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static util.ProfileData.PROFILE_LIST;

public class UserData {

    private static final User FIRST;
    private static final User SECOND;
    private static final User THIRD;

    static {
        FIRST = new User("first1", "first123@mail.ru", "first", 20);
        SECOND = new User("second2", "second123@google.com", "second", 30);
        THIRD = new User("third3", "third123@yandex.ru", "third", 50);
    }

    public static final List<User> USER_LIST = new ArrayList<>(Arrays.asList(
            FIRST, SECOND, THIRD
    ));
}
