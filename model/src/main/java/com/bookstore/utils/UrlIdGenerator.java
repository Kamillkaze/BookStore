package com.bookstore.utils;

public class UrlIdGenerator {
    public static String generate(String author, String title) {
        return (author.toLowerCase() + "-" + title.toLowerCase())
                        .replace(' ', '-');
    }
}
