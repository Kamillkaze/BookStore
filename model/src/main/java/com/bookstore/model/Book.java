package com.bookstore.model;

import java.math.BigDecimal;
import java.util.List;

public class Book {
    private Long id;
    private String title;
    private String author;
    private Integer stars;
    private BigDecimal price;
    private boolean favorite;
    private String imageUrl;
    private List<String> tags;
}