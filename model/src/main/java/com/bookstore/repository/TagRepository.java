package com.bookstore.repository;

import com.bookstore.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, String> {
    Optional<Tag> findByName(String name);

    Optional<Tag> deleteByName(String name);
}
