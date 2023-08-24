package com.bookstore.repository;

import com.bookstore.model.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TagRepository extends MongoRepository<Tag, String> {
    Optional<Tag> findByName(String name);

    Optional<Tag> deleteByName(String name);
}
