package com.bookstore.repository;

import com.bookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.tags")
    List<Book> findAllBooks();

    Optional<Book> findByUrlId(String urlId);

    @Modifying
    int deleteByUrlId(String urlId);

    @Query("""
            SELECT b FROM Book b LEFT JOIN FETCH b.tags t WHERE b.id IN (
            SELECT b2.id FROM Book b2 JOIN b2.tags t2 WHERE t2.name = :tagName)
            """)
    List<Book> findByTagName(@Param(value = "tagName") String tagName);

    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.tags WHERE b.urlId LIKE %:phrase%")
    List<Book> findByUrlIdContainingPhrase(@Param(value = "phrase") String phrase);
}
