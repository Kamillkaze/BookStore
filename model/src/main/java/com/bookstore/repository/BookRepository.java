package com.bookstore.repository;

import com.bookstore.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    Optional<Book> findByUrlId(String urlId);

    List<Book> findByTagsContainingIgnoreCase(String tag);

    List<Book> findByTitleContainingIgnoreCase(String phrase);
}
