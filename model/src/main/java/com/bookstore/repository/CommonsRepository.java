package com.bookstore.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class CommonsRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public CommonsRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void addBookToTag(Long tagId, Long bookId) {
        entityManager.createNativeQuery("INSERT INTO book_tag (tag_id, book_id) VALUES (:tagId, :bookId)")
                .setParameter("tagId", tagId)
                .setParameter("bookId", bookId)
                .executeUpdate();
    }
}
