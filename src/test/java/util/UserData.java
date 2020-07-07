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
        FIRST = new User("first", "first", "first");
        SECOND = new User("second", "second", "second");
        THIRD = new User("third", "third", "third");
    }

    public static final List<User> USER_LIST = new ArrayList<>(Arrays.asList(
            FIRST, SECOND, THIRD
    ));
}
