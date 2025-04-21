package com.api.urlshorter.utils;

public class InputSanitizer {
    public static String sanitize(String input) {
        return input.replaceAll("[^a-zA-Z0-9:/._-]", "");
    }
}