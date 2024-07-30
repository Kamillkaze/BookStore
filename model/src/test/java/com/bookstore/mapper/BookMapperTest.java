package com.bookstore.mapper;

import com.bookstore.dto.BookDto;
import com.bookstore.dto.BookDtoBuilder;
import com.bookstore.model.Book;
import com.bookstore.model.BookBuilder;
import com.bookstore.model.Tag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ContextConfiguration(classes = TestConfigMapper.class)
class BookMapperTest {

    @Autowired
    private BookMapper bookMapper;

    @Test
    @DisplayName("Should map Book to BookDto correctly")
    void shouldMapBookToBookDto() {
        Tag tag1 = new Tag("Tag1");
        Tag tag2 = new Tag("Tag2");
        List<Tag> tags = List.of(tag1, tag2);
        Book input = new BookBuilder().urlId("Author 1", "Test 1").title("Test 1").author("Author 1").stars(1).price(new BigDecimal("1.99")).favorite(false).imageUrl("test/1").tags(tags).build();
        BookDto expected = new BookDtoBuilder().urlId("author-1-test-1").title("Test 1").author("Author 1").stars(1).price(new BigDecimal("1.99")).favorite(false).imageUrl("test/1").tags(tags).build();

        BookDto result = bookMapper.toDto(input);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should map Book to BookDto correctly when tags are null in Book")
    void shouldMapBookToBookDtoWhenTagsNull() {
        Book input = new BookBuilder().urlId("Author 1", "Test 1").title("Test 1").author("Author 1").stars(1).price(new BigDecimal("1.99")).favorite(false).imageUrl("test/1").tags(null).build();
        BookDto expected = new BookDtoBuilder().urlId("author-1-test-1").title("Test 1").author("Author 1").stars(1).price(new BigDecimal("1.99")).favorite(false).imageUrl("test/1").tags(null).build();

        BookDto result = bookMapper.toDto(input);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should map BookDto to Book correctly with usage of UrlIdGenerator")
    void shouldMapBookDtoToBookWithUsageOfUrlIdGenerator() {
        BookDto input = new BookDtoBuilder().title("Test 1").author("Author 1").stars(1).price(new BigDecimal("1.99")).favorite(false).imageUrl("test/1").tags(null).build();
        Book expected = new BookBuilder().urlId("Author 1", "Test 1").title("Test 1").author("Author 1").stars(1).price(new BigDecimal("1.99")).favorite(false).imageUrl("test/1").tags(null).build();

        Book result = bookMapper.toEntity(input);

        assertThat(result).isEqualTo(expected);
    }
}