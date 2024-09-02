package com.bookstore.model;

import org.assertj.core.api.AssertionsForInterfaceTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class TagTest {

    @Test
    @DisplayName("Equals method should return true when comparing with itself")
    void testEqualsWithSelf() {
        Tag tag = new Tag();
        assertThat(tag).isEqualTo(tag);
    }

    @Test
    @DisplayName("Equals method should return false when comparing with null")
    void testEqualsWithNull() {
        Tag tag = new Tag();
        assertThat(tag).isNotEqualTo(null);
    }

    @Test
    @DisplayName("Equals method should return false when comparing with a different class object")
    void testEqualsWithDifferentClass() {
        Tag tag = new Tag();
        String differentClassObject = "I am not a Tag";
        assertThat(tag).isNotEqualTo(differentClassObject);
    }

    @Test
    @DisplayName("Equals method should return true when all fields match")
    void testEqualsWhenAllFieldsMatch() {
        Tag tag1 = new Tag("tag");
        tag1.setId(1L);
        tag1.setBooks(List.of(new Book("urlId", "title", "author")));

        Tag tag2 = new Tag("tag");
        tag2.setId(1L);
        tag2.setBooks(List.of(new Book("urlId", "title", "author")));

        assertThat(tag1).isEqualTo(tag2);
    }

    @Test
    @DisplayName("Equals method should return false when a non-primitive field differs")
    void testEqualsWhenNonPrimitiveFieldDiffers() {
        Tag tag1 = new Tag("tag1");
        tag1.setId(1L);
        tag1.setBooks(List.of(new Book("urlId", "title", "author")));

        Tag tag2 = new Tag("tag2");
        tag2.setId(1L);
        tag2.setBooks(List.of(new Book("urlId", "title", "author")));

        assertThat(tag1).isNotEqualTo(tag2);
    }

    @Test
    @DisplayName("Equals method should return false when a primitive field differs")
    void testEqualsWhenPrimitiveFieldDiffers() {
        Tag tag1 = new Tag("tag");
        tag1.setId(1L);
        tag1.setBooks(List.of(new Book("urlId", "title", "author")));

        Tag tag2 = new Tag("tag");
        tag2.setId(2L);
        tag2.setBooks(List.of(new Book("urlId", "title", "author")));

        assertThat(tag1).isNotEqualTo(tag2);
    }

    @Test
    @DisplayName("Equals method should return false when comparing two objects with different non-null fields")
    void testEqualsWithDifferentNonNullFields() {
        Tag tag1 = new Tag();
        tag1.setId(1L);

        Tag tag2 = new Tag();
        tag2.setId(2L);

        assertThat(tag1).isNotEqualTo(tag2);
    }

    @Test
    @DisplayName("HashCode should be consistent for the same object")
    void testHashCodeConsistency() {
        Tag tag = new Tag("name");
        tag.setId(1L);
        tag.setBooks(List.of(new Book("urlId", "title", "author")));

        int initialHashCode = tag.hashCode();
        assertThat(tag.hashCode()).isEqualTo(initialHashCode);
    }

    @Test
    @DisplayName("HashCode should be equal for two equal objects")
    void testHashCodeEqualityForEqualObjects() {
        Tag tag1 = new Tag("name");
        tag1.setId(1L);
        tag1.setBooks(List.of(new Book("urlId", "title", "author")));

        Tag tag2 = new Tag("name");
        tag2.setId(1L);
        tag2.setBooks(List.of(new Book("urlId", "title", "author")));

        assertThat(tag1.hashCode()).isEqualTo(tag2.hashCode());
    }

    @Test
    @DisplayName("HashCode should differ for two different objects")
    void testHashCodeDifferenceForDifferentObjects() {
        Tag tag1 = new Tag("name1");
        tag1.setId(1L);
        tag1.setBooks(List.of(new Book("urlId1", "title1", "author1")));

        Tag tag2 = new Tag("name2");
        tag2.setId(2L);
        tag2.setBooks(List.of(new Book("urlId2", "title2", "author2")));

        assertThat(tag1.hashCode()).isNotEqualTo(tag2.hashCode());
    }

    @Test
    @DisplayName("HashCode should work correctly when fields are null")
    void testHashCodeWithNullFields() {
        Tag tag1 = new Tag();
        tag1.setId(null);
        tag1.setBooks(null);

        Tag tag2 = new Tag();
        tag2.setId(null);
        tag2.setBooks(null);

        assertThat(tag1.hashCode()).isEqualTo(tag2.hashCode());
    }

    @Test
    @DisplayName("HashCode should work correctly in collections")
    void testHashCodeInCollections() {
        Tag tag1 = new Tag("name");
        tag1.setId(1L);
        tag1.setBooks(List.of(new Book("urlId", "title", "author")));

        Tag tag2 = new Tag("name");
        tag2.setId(1L);
        tag2.setBooks(List.of(new Book("urlId", "title", "author")));

        Set<Tag> tagSet = new HashSet<>();
        tagSet.add(tag1);
        tagSet.add(tag2); // tag2 should not be added since it's equal to tag1

        AssertionsForInterfaceTypes.assertThat(tagSet).hasSize(1);
    }
}