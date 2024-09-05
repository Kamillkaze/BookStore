package com.bookstore.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.bookstore.dto.TagDto;
import com.bookstore.model.Book;
import com.bookstore.model.Tag;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = TestConfigMapper.class)
class TagMapperTest {

    @Autowired private TagMapper tagMapper;

    @Test
    @DisplayName("Should map Tag to dto correctly")
    void shouldMapTag() {
        TagDto expected = new TagDto("tag1", 3L);
        List<Book> books =
                List.of(
                        new Book("urlId1", "title1", "author1"),
                        new Book("urlId2", "title2", "author2"),
                        new Book("urlId3", "title3", "author3"));
        Tag tag = new Tag("tag1");
        tag.setBooks(books);

        TagDto dto = tagMapper.toDto(tag);

        assertThat(dto).isEqualTo(expected);
    }
}
