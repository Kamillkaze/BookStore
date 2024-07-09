package com.bookstore.dto;

public class TagDto {

    public TagDto(String name, Long count) {
        this.name = name;
        this.count = count;
    }

    private final String name;

    private final Long count;

    public String getName() {
        return name;
    }

    public Long getCount() {
        return count;
    }
}
