package com.bookstore.dto;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TagDto tagDto = (TagDto) o;
        return Objects.equals(name, tagDto.name) && Objects.equals(count, tagDto.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, count);
    }
}
