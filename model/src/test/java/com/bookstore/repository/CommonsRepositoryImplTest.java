package com.bookstore.repository;

import com.bookstore.TestConfig;
import com.bookstore.model.Book;
import com.bookstore.model.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
class CommonsRepositoryImplTest {

    @Autowired
    private CommonsRepositoryImpl commonsRepositoryImpl;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    @DisplayName("Should add a row with specified book_id and tag_id to book_tag join table")
    void addBookToTag() {
        Book book = new Book();
        Tag tag = new Tag();
        entityManager.persist(book);
        entityManager.persist(tag);
        entityManager.flush();

        commonsRepositoryImpl.addBookToTag(tag.getId(), book.getId());
        List<?> result = entityManager.createNativeQuery("select * from book_tag where book_id = :bookId and tag_id = :tagId")
                .setParameter("tagId", tag.getId())
                .setParameter("bookId", book.getId())
                .getResultList();

        assertThat(result).hasSize(1);
    }

    @Test
    @DisplayName("Should remove a row with specified book_id and tag_id from book_tag join table")
    void removeBookFromTag() {
        Book book = new Book();
        Tag tag = new Tag();
        entityManager.persist(book);
        entityManager.persist(tag);
        entityManager.flush();
        commonsRepositoryImpl.addBookToTag(tag.getId(), book.getId());

        commonsRepositoryImpl.removeBookFromTag(tag.getId(), book.getId());
        List<?> result = entityManager.createNativeQuery("select * from book_tag where book_id = :bookId and tag_id = :tagId")
                .setParameter("tagId", tag.getId())
                .setParameter("bookId", book.getId())
                .getResultList();

        assertThat(result).isEmpty();
    }
}