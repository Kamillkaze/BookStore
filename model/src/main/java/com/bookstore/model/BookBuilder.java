package com.bookstore.model;

import com.bookstore.utils.UrlIdGenerator;
import java.math.BigDecimal;
import java.util.List;

public class BookBuilder {

    private Long id;
    private String urlId;
    private String title;
    private String author;
    private Integer stars;
    private BigDecimal price;
    private boolean favorite;
    private String imageUrl;
    private List<Tag> tags;

    public BookBuilder id(Long id) {
        this.id = id;
        return this;
    }

    public BookBuilder title(String title) {
        this.title = title;
        return this;
    }

    public BookBuilder urlId(String author, String title) {
        this.urlId = UrlIdGenerator.generate(author, title);
        return this;
    }

    public BookBuilder author(String author) {
        this.author = author;
        return this;
    }

    public BookBuilder stars(Integer stars) {
        this.stars = stars;
        return this;
    }

    public BookBuilder price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public BookBuilder favorite(boolean favorite) {
        this.favorite = favorite;
        return this;
    }

    public BookBuilder imageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public BookBuilder tags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public Book build() {
        return new Book(
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
