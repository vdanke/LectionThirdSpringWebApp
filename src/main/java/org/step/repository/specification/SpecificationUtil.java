package org.step.repository.specification;

public class SpecificationUtil {

    public static String wrapWithLikeLowerCase(String str) {
        final String like = "%";

        return (like + str + like).toLowerCase();
    }

    public static String wrapWithLike(String str) {
        final String like = "%";

        return like + str + like;
    }
}
