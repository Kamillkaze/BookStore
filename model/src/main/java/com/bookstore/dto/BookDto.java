package com.bookstore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookDto bookDto = (BookDto) o;
        return favorite == bookDto.favorite && Objects.equals(id, bookDto.id) && Objects.equals(urlId, bookDto.urlId) && Objects.equals(title, bookDto.title) && Objects.equals(author, bookDto.author) && Objects.equals(stars, bookDto.stars) && Objects.equals(price, bookDto.price) && Objects.equals(imageUrl, bookDto.imageUrl) && Objects.equals(tags, bookDto.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, urlId, title, author, stars, price, favorite, imageUrl, tags);
    }
}
