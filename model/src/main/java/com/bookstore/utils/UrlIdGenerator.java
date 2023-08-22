package com.bookstore.utils;

import org.apache.commons.lang3.StringUtils;

public class UrlIdGenerator {
    public static String generate(String author, String title) {
        // Doesn't convert "ł"
        String urlId = (author.toLowerCase() + "-" + title.toLowerCase())
                .replace(' ', '-');

        return StringUtils. stripAccents(urlId);
    }
}
