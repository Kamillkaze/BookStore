package com.bookstore.mapper;

import com.bookstore.dto.TagDto;
import com.bookstore.model.Tag;
import org.springframework.stereotype.Component;

@Component
public class TagMapper {
    public TagDto toDto(Tag tag) {
        return new TagDto(tag.getName(), (long) tag.getBooks().size());
    }
}
