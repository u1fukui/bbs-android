package com.u1fukui.bbs.utils;


public class StringUtils {

    private StringUtils() {}

    public static boolean isBlank(String text) {
        if (text == null) {
            return true;
        }
        return text.trim().isEmpty();
    }

    public static int isLength(String text) {
        if (text == null) {
            return 0;
        }
        return text.length();
    }
}
