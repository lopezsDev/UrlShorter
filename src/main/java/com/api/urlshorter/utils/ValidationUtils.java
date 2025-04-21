package com.api.urlshorter.utils;


import org.apache.commons.validator.routines.UrlValidator;

public class ValidationUtils {
    private static final UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});

    public static boolean isValidUrl(String url) {
        return urlValidator.isValid(url);
    }
}