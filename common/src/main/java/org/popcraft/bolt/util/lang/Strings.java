package org.popcraft.bolt.util.lang;

public class Strings {
    private Strings() {
    }

    public static String toTitleCase(final Object obj) {
        return toTitleCase(obj == null ? "null" : obj.toString());
    }

    public static String toTitleCase(final String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        final StringBuilder builder = new StringBuilder();
        final char[] chars = str.toCharArray();
        builder.append(Character.toUpperCase(chars[0]));
        for (int i = 1; i < chars.length; ++i) {
            builder.append(Character.isWhitespace(chars[i - 1]) ? Character.toUpperCase(i) : chars[i]);
        }
        return builder.toString();
    }
}
