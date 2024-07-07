package com.bookstore.repository;

import com.bookstore.model.Book;
import com.bookstore.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByUrlId(String urlId);

    @Modifying
    int deleteByUrlId(String urlId);

    List<Book> findByTagsContainingIgnoreCase(Tag tag);

    List<Book> findByUrlIdContainingIgnoreCase(String phrase);
}
