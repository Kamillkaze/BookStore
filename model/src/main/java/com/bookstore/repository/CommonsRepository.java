package com.bookstore.repository;

public interface CommonsRepository {
    void addBookToTag(Long tagId, Long bookId);
    void removeBookFromTag(Long tagId, Long bookId);
}
