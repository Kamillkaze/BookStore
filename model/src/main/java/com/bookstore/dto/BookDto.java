package com.bookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;

public class BookDto {

    private Long id;
    private String urlId;
    @NotBlank(message = "Title should not be blank")
    private String title;
    @NotBlank(message = "Author should not be blank")
    private String author;
    private Integer stars;
    @NotNull(message = "Price should not be null")
    @Min(value = 1, message = "Price should be greater or equal to 1.00")
    private BigDecimal price;
    private boolean favorite;
    @NotNull(message = "Image should not be null")
    private String imageUrl;
    @NotNull(message = "Tags can be an empty list but never null")
    private List<String> tags;

    public BookDto() {
    }

    BookDto(Long id, String urlId, String title, String author, Integer stars, BigDecimal price, boolean favorite, String imageUrl, List<String> tags) {
        this.id = id;
        this.urlId = urlId;
        this.title = title;
        this.author = author;
        this.stars = stars;
        this.price = price;
        this.favorite = favorite;
        this.imageUrl = imageUrl;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public String getUrlId() {
        return urlId;
    }

    public String getTitle() {
        return title;
    }

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
