package com.bookstore.mapper;

import com.bookstore.dto.BookDto;
import com.bookstore.dto.BookDtoBuilder;
import com.bookstore.model.Book;
import com.bookstore.model.BookBuilder;
import org.springframework.stereotype.Component;

@Component
public class BookMapper {
    public BookDto toDto(Book book) {
        return new BookDtoBuilder()
                .urlId(book.getUrlId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .stars(book.getStars())
                .price(book.getPrice())
                .favorite(book.isFavorite())
                .imageUrl(book.getImageUrl())
                .tags(book.getTags())
                .build();
    }

    public Book toDocument(BookDto dto) {
        return new BookBuilder()
                .urlId(dto.getAuthor(), dto.getTitle())
                .title(dto.getTitle())
                .author(dto.getAuthor())
                .stars(dto.getStars())
                .price(dto.getPrice())
                .favorite(dto.isFavorite())
                .imageUrl(dto.getImageUrl())
                .tags(dto.getTags())
                .build();
    }
}
