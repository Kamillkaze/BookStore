package com.bookstore.exception;

public class CustomTimestampException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Timestamps are generated automatically and should not be passed in request body";
    }
}
