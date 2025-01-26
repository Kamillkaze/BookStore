package com.bookstore.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import org.springframework.lang.NonNull;

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

    @Column(name = "last_modified")
    private LocalDateTime lastModified;

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {CascadeType.MERGE, CascadeType.DETACH, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(
            name = "book_tag",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

    public Book(@NonNull String urlId, @NonNull String title, @NonNull String author) {
        this.urlId = urlId;
        this.title = title;
        this.author = author;
    }

    Book(
            Long id,
            @NonNull String urlId,
            @NonNull String title,
            @NonNull String author,
            Integer stars,
            BigDecimal price,
            boolean favorite,
            String imageUrl,
            LocalDateTime lastModified,
            List<Tag> tags) {
        this.id = id;
        this.urlId = urlId;
        this.title = title;
        this.author = author;
        this.stars = stars;
        this.price = price;
        this.favorite = favorite;
        this.imageUrl = imageUrl;
        this.lastModified = lastModified;
        this.tags = tags;
    }

    public Book() {}

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

    public LocalDateTime getLastModified() {
        return lastModified;
    }

    public List<Tag> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return favorite == book.favorite
                && Objects.equals(id, book.id)
                && Objects.equals(urlId, book.urlId)
                && Objects.equals(title, book.title)
                && Objects.equals(author, book.author)
                && Objects.equals(stars, book.stars)
                && Objects.equals(price, book.price)
                && Objects.equals(imageUrl, book.imageUrl)
                && Objects.equals(lastModified, book.lastModified)
                && Objects.equals(tags, book.tags);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                id, urlId, title, author, stars, price, favorite, imageUrl, lastModified, tags);
    }
}
