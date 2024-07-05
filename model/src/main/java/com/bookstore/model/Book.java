package com.bookstore.model;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "book")
public class Book {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(unique = true, name = "url_id")
    @NonNull
    private String urlId;

    @NonNull
    @Column(name = "title")
    private String title;

    @NonNull
    @Column(name = "author")
    private String author;

    @Column(name = "stars")
    private Integer stars;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "favorite")
    private boolean favorite;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = { CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH }
    )
    @JoinTable(
        name = "book_tag",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;

    public Book(@NonNull String urlId, @NonNull String title, @NonNull String author) {
        this.urlId = urlId;
        this.title = title;
        this.author = author;
    }
    Book(Long id, @NonNull String urlId, @NonNull String title, @NonNull String author, Integer stars, BigDecimal price, boolean favorite, String imageUrl, List<Tag> tags) {
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

    public Book() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
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

    public List<Tag> getTags() {
        return tags;
    }
}