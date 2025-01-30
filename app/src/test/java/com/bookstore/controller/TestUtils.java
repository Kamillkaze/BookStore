package com.bookstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;

public class TestUtils {

    @Autowired private ObjectMapper objectMapper;

    private static final String JSON_PATH = "src/test/resources/";

    public <T> T readJsonFile(String fileName, Class<T> classType) throws IOException {
        return this.objectMapper.readValue(new File(JSON_PATH + fileName), classType);
    }
}
