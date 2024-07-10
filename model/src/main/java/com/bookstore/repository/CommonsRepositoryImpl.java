package com.bookstore.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
public class CommonsRepositoryImpl implements CommonsRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public CommonsRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public void addBookToTag(Long tagId, Long bookId) {
        entityManager.createNativeQuery("INSERT INTO book_tag (tag_id, book_id) VALUES (:tagId, :bookId)")
                .setParameter("tagId", tagId)
                .setParameter("bookId", bookId)
                .executeUpdate();
    }


    @Transactional
    public void removeBookFromTag(Long tagId, Long bookId) {
        entityManager.createNativeQuery("DELETE FROM book_tag WHERE tag_id = :tagId AND book_id = :bookId")
                .setParameter("tagId", tagId)
                .setParameter("bookId", bookId)
                .executeUpdate();
    }
}
