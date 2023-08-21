package com.bookstore.dto;

import java.math.BigDecimal;
import java.util.List;

public class BookDtoBuilder {

    private String urlId;
    private String title;
    private String author;
    private Integer stars;
    private BigDecimal price;
    private boolean favorite;
    private String imageUrl;
    private List<String> tags;

    public BookDtoBuilder urlId(String urlId) {
        this.urlId = urlId;
        return this;
    }
    public BookDtoBuilder title(String title) {
        this.title = title;
        return this;
    }

    public BookDtoBuilder author(String author) {
        this.author = author;
        return this;
    }

    public BookDtoBuilder stars(Integer stars) {
        this.stars = stars;
        return this;
    }

    public BookDtoBuilder price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public BookDtoBuilder favorite(boolean favorite) {
        this.favorite = favorite;
        return this;
    }

    public BookDtoBuilder imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public BookDtoBuilder tags(List<String> tags) {
        this.tags = tags;
        return this;
    }
    public BookDto build() {
        return new BookDto(urlId, title, author, stars, price, favorite, imageUrl, tags);
    }
}
