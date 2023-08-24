package com.bookstore.dto;

public class TagDto {

    public TagDto(String name, Integer count) {
        this.name = name;
        this.count = count;
    }

    private final String name;

    private final Integer count;

    public String getName() {
        return name;
    }

    public Integer getCount() {
        return count;
    }
}
