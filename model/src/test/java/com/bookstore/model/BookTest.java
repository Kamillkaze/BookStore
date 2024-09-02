package com.bookstore.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BookTest {
    @Test
    @DisplayName("Equals method should return true when comparing with itself")
    void testEqualsWithSelf() {
        Book book = new Book();
        assertThat(book).isEqualTo(book);
    }

    @Test
    @DisplayName("Equals method should return false when comparing with null")
    void testEqualsWithNull() {
        Book book = new Book();
        assertThat(book).isNotEqualTo(null);
    }

    @Test
    @DisplayName("Equals method should return false when comparing with a different class object")
    void testEqualsWithDifferentClass() {
        Book book = new Book();
        String differentClassObject = "I am not a Book";
        assertThat(book).isNotEqualTo(differentClassObject);
    }

    @Test
    @DisplayName("Equals method should return true when all fields match")
    void testEqualsWhenAllFieldsMatch() {
        Book book1 = new BookBuilder()
                .id(1L)
                .urlId("author", "title")
                .title("title")
                .author("author")
                .stars(4)
                .price(new BigDecimal("12.99"))
                .imageUrl("image.png")
                .tags(List.of(new Tag("name1"), new Tag("name2")))
                .favorite(false)
                .build();

        Book book2 = new BookBuilder()
                .id(1L)
                .urlId("author", "title")
                .title("title")
                .author("author")
                .stars(4)
                .price(new BigDecimal("12.99"))
                .imageUrl("image.png")
                .tags(List.of(new Tag("name1"), new Tag("name2")))
                .favorite(false)
                .build();

        assertThat(book1).isEqualTo(book2);
    }

    @Test
    @DisplayName("Equals method should return false when a non-primitive field differs")
    void testEqualsWhenNonPrimitiveFieldDiffers() {
        Book book1 = new BookBuilder()
                .id(1L)
                .urlId("author", "title")
                .title("title")
                .author("author")
                .stars(4)
                .price(new BigDecimal("12.99"))
                .imageUrl("image.png")
                .tags(List.of(new Tag("name1"), new Tag("name2")))
                .favorite(false)
                .build();

        Book book2 = new BookBuilder()
                .id(1L)
                .urlId("author", "title")
                .title("title")
                .author("different author")
                .stars(4)
                .price(new BigDecimal("12.99"))
                .imageUrl("image.png")
                .tags(List.of(new Tag("name1"), new Tag("name2")))
                .favorite(false)
                .build();

        assertThat(book1).isNotEqualTo(book2);
    }

    @Test
    @DisplayName("Equals method should return false when a primitive field differs")
    void testEqualsWhenPrimitiveFieldDiffers() {
        Book book1 = new BookBuilder()
                .id(1L)
                .urlId("author", "title")
                .title("title")
                .author("author")
                .stars(4)
                .price(new BigDecimal("12.99"))
                .imageUrl("image.png")
                .tags(List.of(new Tag("name1"), new Tag("name2")))
                .favorite(false)
                .build();

        Book book2 = new BookBuilder()
                .id(1L)
                .urlId("author", "title")
                .title("title")
                .author("author")
                .stars(4)
                .price(new BigDecimal("12.99"))
                .imageUrl("image.png")
                .tags(List.of(new Tag("name1"), new Tag("name2")))
                .favorite(true)
                .build();

        assertThat(book1).isNotEqualTo(book2);
    }

    @Test
    @DisplayName("Equals method should return true when comparing two objects with null fields that match")
    void testEqualsWithMatchingNullFields() {
        Book book1 = new Book(null, null, null, null, null,null, false, null, null);
        Book book2 = new Book(null, null, null, null, null,null, false, null, null);

        assertThat(book1).isEqualTo(book2);
    }

    @Test
    @DisplayName("Equals method should return false when comparing two objects with different non-null fields")
    void testEqualsWithDifferentNonNullFields() {
        Book book1 = new Book();
        book1.setId(1L);

        Book book2 = new Book();
        book2.setId(2L);

        assertThat(book1).isNotEqualTo(book2);
    }
}