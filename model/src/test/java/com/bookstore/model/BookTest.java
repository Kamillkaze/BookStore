package com.bookstore.model;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        Book book1 =
                new BookBuilder()
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

        Book book2 =
                new BookBuilder()
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
        Book book1 =
                new BookBuilder()
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

        Book book2 =
                new BookBuilder()
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
        Book book1 =
                new BookBuilder()
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

        Book book2 =
                new BookBuilder()
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
    @DisplayName(
            "Equals method should return true when comparing two objects with null fields that match")
    void testEqualsWithMatchingNullFields() {
        Book book1 = new Book(null, "id", "title", "author", null, null, false, null, null, null);
        Book book2 = new Book(null, "id", "title", "author", null, null, false, null, null, null);

        assertThat(book1).isEqualTo(book2);
    }

    @Test
    @DisplayName(
            "Equals method should return false when comparing two objects with different non-null fields")
    void testEqualsWithDifferentNonNullFields() {
        Book book1 = new Book();
        book1.setId(1L);

        Book book2 = new Book();
        book2.setId(2L);

        assertThat(book1).isNotEqualTo(book2);
    }

    @Test
    @DisplayName("HashCode should be consistent for the same object")
    void testHashCodeConsistency() {
        Book book =
                new BookBuilder()
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

        int initialHashCode = book.hashCode();
        assertThat(book.hashCode()).isEqualTo(initialHashCode);
    }

    @Test
    @DisplayName("HashCode should be equal for two equal objects")
    void testHashCodeEqualityForEqualObjects() {
        Book book1 =
                new BookBuilder()
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

        Book book2 =
                new BookBuilder()
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

        assertThat(book1.hashCode()).isEqualTo(book2.hashCode());
    }

    @Test
    @DisplayName("HashCode should differ for two different objects")
    void testHashCodeDifferenceForDifferentObjects() {
        Book book1 =
                new BookBuilder()
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

        Book book2 =
                new BookBuilder()
                        .id(1L)
                        .urlId("author2", "title2")
                        .title("title2")
                        .author("author2")
                        .stars(4)
                        .price(new BigDecimal("22.99"))
                        .imageUrl("image2.png")
                        .tags(List.of(new Tag("different name1"), new Tag("different name2")))
                        .favorite(true)
                        .build();

        assertThat(book1.hashCode()).isNotEqualTo(book2.hashCode());
    }

    @Test
    @DisplayName("HashCode should work correctly when fields are null")
    void testHashCodeWithNullFields() {
        Book book1 = new Book(null, "id", "title", "author", null, null, false, null, null, null);
        Book book2 = new Book(null, "id", "title", "author", null, null, false, null, null, null);

        assertThat(book1.hashCode()).isEqualTo(book2.hashCode());
    }

    @Test
    @DisplayName("HashCode should work correctly in collections")
    void testHashCodeInCollections() {
        Book book1 =
                new BookBuilder()
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

        Book book2 =
                new BookBuilder()
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

        Set<Book> bookSet = new HashSet<>();
        bookSet.add(book1);
        bookSet.add(book2); // book2 should not be added since it's equal to book1

        assertThat(bookSet).hasSize(1);
    }
}
