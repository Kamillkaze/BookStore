package com.bookstore.model;

import com.bookstore.exception.TagCountBelowZeroException;
import com.mongodb.lang.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Tag {

    public Tag(@NonNull String name) {
        this.name = name;
        this.count = 0;
    }

    @Id
    private String id;

    @NonNull
    @Indexed(unique = true)
    private final String name;

    @NonNull
    private Integer count;

    public Tag incrementCountByOne() {
        this.count++;
        return this;
    }

    public Tag decrementCountByOne() {
        if (this.count <= 0) {
            throw new TagCountBelowZeroException();
        }
        this.count--;

        return this;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public Integer getCount() {
        return count;
    }
}
