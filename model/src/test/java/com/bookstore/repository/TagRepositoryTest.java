package com.bookstore.repository;

import com.bookstore.TestConfig;
import com.bookstore.dto.TagDto;
import com.bookstore.model.Book;
import com.bookstore.model.BookBuilder;
import com.bookstore.model.Tag;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
class TagRepositoryTest {

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private BookRepository bookRepository;

    private List<Tag> savedTags;

    @BeforeEach
    void setUp() {

        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        Tag tag3 = new Tag("tag3");
        List<Tag> tags = List.of(tag1, tag2, tag3);

        savedTags = tagRepository.saveAll(tags);

        Book book1 = new BookBuilder().urlId("Author 1", "Test 1").title("Test 1").author("Author 1").stars(1).price(new BigDecimal("1.99")).favorite(false).imageUrl("test/1").tags(new ArrayList<>()).build();
        Book book2 = new BookBuilder().urlId("Author 2", "Test 2").title("Test 2").author("Author 2").stars(2).price(new BigDecimal("2.99")).favorite(true).imageUrl("test/2").tags(new ArrayList<>()).build();
        Book book3 = new BookBuilder().urlId("Author 3", "Test 3").title("Test 3").author("Author 3").stars(3).price(new BigDecimal("3.99")).favorite(false).imageUrl("test/3").tags(new ArrayList<>()).build();
        Book book4 = new BookBuilder().urlId("Author 4", "Test 4").title("Test 4").author("Author 4").stars(4).price(new BigDecimal("4.99")).favorite(false).imageUrl("test/4").tags(new ArrayList<>()).build();
        Book book5 = new BookBuilder().urlId("Author 5", "Test 5").title("Test 5").author("Author 5").stars(5).price(new BigDecimal("5.99")).favorite(true).imageUrl("test/5").tags(new ArrayList<>()).build();
        List<Book> booksToSave = List.of(book1, book2, book3, book4, book5);

        book1.getTags().add(tag1);
        book1.getTags().add(tag2);
        book1.getTags().add(tag3);
        book4.getTags().add(tag1);
        book4.getTags().add(tag3);
        book5.getTags().add(tag2);
        bookRepository.saveAll(booksToSave);
    }

    @Test
    @DisplayName("Should throw when attempting to add a tag with non unique name")
    void throwWhenTagNameIsNotUnique() {
        assertThatThrownBy(() -> tagRepository.save(new Tag("tag1")))
                .hasRootCauseInstanceOf(JdbcSQLIntegrityConstraintViolationException.class);
    }

    @Test
    @DisplayName("Should return empty list of TagDto if db empty")
    void getAllTagsWhenDbEmpty() {
        bookRepository.deleteAll();
        tagRepository.deleteAll();

        List<TagDto> allTags = tagRepository.getAllTags();

        assertThat(allTags).isEmpty();
    }

    @Test
    @DisplayName("Should return a list of all tags with their count")
    void getAllTagsWhenDbContainsTags() {
        List<TagDto> expected = List.of(
                new TagDto("tag1", 2L),
                new TagDto("tag2", 2L),
                new TagDto("tag3", 2L)
        );

        List<TagDto> allTags = tagRepository.getAllTags();

        assertThat(allTags).hasSize(3);
        assertThat(allTags).hasSameElementsAs(expected);
    }

    @Test
    @DisplayName("Should return empty Optional if name doesn't exist in a db")
    void findByNameWhenNameNotInDb() {
        Optional<Tag> returned = tagRepository.findByName("dummyname");

        assertThat(returned).isEmpty();
    }

    @Test
    @DisplayName("Should return Optional with Tag correctly if name exists in a db")
    void findByNameWhenNameInDb() {
        Optional<Tag> returned = tagRepository.findByName("tag3");

        assertThat(returned.orElseThrow()).isEqualTo(savedTags.get(2));
    }

    @Test
    @DisplayName("Should return 0 if tag with a specified name doesn't exist in db")
    void deleteByNameWhenNameNotInDb() {
        int rowsAffected = tagRepository.deleteByName("random");

        assertThat(rowsAffected).isEqualTo(0);
    }

    @Test
    @DisplayName("Should return 1 if tag with a specified name exists in db")
    void deleteByNameWhenNameInDb() {
        int rowsAffected = tagRepository.deleteByName("tag2");

        assertThat(rowsAffected).isEqualTo(1);
    }
}