package com.bookstore.dto;

import com.bookstore.model.Tag;
import java.math.BigDecimal;
import java.util.List;

public class BookDtoBuilder {

    private Long id;
    private String urlId;
    private String title;
    private String author;
    private Integer stars;
    private BigDecimal price;
    private boolean favorite;
    private String imageUrl;
    private List<String> tags;

    public BookDtoBuilder id(Long id) {
        this.id = id;
        return this;
    }

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

    public BookDtoBuilder tags(List<Tag> tags) {
        if (tags != null && !tags.isEmpty()) {
            this.tags = tags.stream().map(Tag::getName).toList();
        }

        return this;
    }

    public BookDto build() {
        return new BookDto(
                this.id,
                this.urlId,
                this.title,
                this.author,
                this.stars,
                this.price,
                this.favorite,
                this.imageUrl,
                this.tags);
    }
}
