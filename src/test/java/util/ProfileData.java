package util;

import org.step.model.Profile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProfileData {

    private static final Profile FIRST = new Profile("docker", "I know everything about docker");
    private static final Profile SECOND = new Profile("java", "I know java");
    private static final Profile THIRD = new Profile(".net", "I know .net");

    public static final List<Profile> PROFILE_LIST = new ArrayList<>(
            Arrays.asList(FIRST, SECOND, THIRD)
    );
}
