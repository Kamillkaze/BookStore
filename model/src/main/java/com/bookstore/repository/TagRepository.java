package com.bookstore.repository;

import com.bookstore.dto.TagDto;
import com.bookstore.model.Tag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, String> {

    @Query(
            "SELECT new com.bookstore.dto.TagDto(t.name, COUNT(b)) FROM Tag t LEFT JOIN t.books b GROUP BY t.name")
    List<TagDto> getAllTags();

    Optional<Tag> findByName(String name);

    @Modifying
    int deleteByName(String name);
}
