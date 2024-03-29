package com.bookstore.model;

import com.mongodb.lang.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.List;

@Document
public class Book {

    Book(@NonNull String urlId, @NonNull String title, @NonNull String author, Integer stars, BigDecimal price, boolean favorite, String imageUrl, List<String> tags) {
        this.urlId = urlId;
        this.title = title;
        this.author = author;
        this.stars = stars;
        this.price = price;
        this.favorite = favorite;
        this.imageUrl = imageUrl;
        this.tags = tags;
    }

    @Id
    private String id;
    @Indexed(unique = true)
    @NonNull
    private final String urlId;
    @NonNull
    private final String title;
    @NonNull
    private final String author;
    private Integer stars;
    private BigDecimal price;
    private boolean favorite;
    private String imageUrl;
    private List<String> tags;

    public String getId() {
        return id;
    }

    @NonNull
    public String getUrlId() {
        return urlId;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getAuthor() {
        return author;
    }

    public Integer getStars() {
        return stars;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public List<String> getTags() {
        return tags;
    }
}