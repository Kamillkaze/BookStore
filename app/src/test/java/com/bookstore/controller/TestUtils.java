package com.bookstore.controller;

import com.bookstore.dto.BookDtoBuilder;
import java.math.BigDecimal;
import java.util.List;

public class TestUtils {

    private static final String RESPONSE_1 =
            "[{\"id\":1,\"urlId\":\"author-1-title-1\",\"title\":\"Title 1\",\"author\":\"Author 1\",\"stars\":2,\"price\":2.99,\"favorite\":false,\"imageUrl\":\"image/1\",\"tags\":[\"tag1\"]},{\"id\":2,\"urlId\":\"author-2-title-2\",\"title\":\"Title 2\",\"author\":\"Author 2\",\"stars\":5,\"price\":3.99,\"favorite\":true,\"imageUrl\":\"image/2\",\"tags\":null}]";

    private static final String RESPONSE_2 =
            "{\"id\":1,\"urlId\":\"author-1-title-1\",\"title\":\"Title 1\",\"author\":\"Author 1\",\"stars\":2,\"price\":2.99,\"favorite\":false,\"imageUrl\":\"image/1\",\"tags\":[\"tag1\"]}";

    private static final String RESPONSE_3 =
            "[{\"id\":1,\"urlId\":\"author-1-title-1\",\"title\":\"Title 1\",\"author\":\"Author 1\",\"stars\":2,\"price\":2.99,\"favorite\":false,\"imageUrl\":\"image/1\",\"tags\":[\"tag1\"]},{\"id\":2,\"urlId\":\"author-2-title-2\",\"title\":\"Title 2\",\"author\":\"Author 2\",\"stars\":5,\"price\":3.99,\"favorite\":true,\"imageUrl\":\"image/2\",\"tags\":[\"tag1\"]}]";

    private static final String RESPONSE_4 =
            "[{\"id\":1,\"urlId\":\"author-1-title-1\",\"title\":\"Title 1\",\"author\":\"Author 1\",\"stars\":2,\"price\":2.99,\"favorite\":false,\"imageUrl\":\"image/1\",\"tags\":null},{\"id\":2,\"urlId\":\"author-2-title-2\",\"title\":\"Title 2\",\"author\":\"Author 2\",\"stars\":5,\"price\":3.99,\"favorite\":true,\"imageUrl\":\"image/2\",\"tags\":null}]";

    private static final String RESPONSE_5 =
            "{\"author\":\"Author should not be blank\",\"title\":\"Title should not be blank\"}";

    private static final String RESPONSE_6 =
            "{\"id\":1,\"urlId\":\"author-1-title-1\",\"title\":\"Title 1\",\"author\":\"Author 1\",\"stars\":2,\"price\":2.99,\"favorite\":false,\"imageUrl\":\"image/1\",\"tags\":null}";

    public static List<BookDtoBuilder> getDefaultBookDtos() {
        BookDtoBuilder bookDto1 = new BookDtoBuilder()
                .id(1L)
                .urlId("author-1-title-1")
                .title("Title 1")
                .author("Author 1")
                .stars(2)
                .price(new BigDecimal("2.99"))
                .favorite(false)
                .imageUrl("image/1");

        BookDtoBuilder bookDto2 = new BookDtoBuilder()
                .id(2L)
                .urlId("author-2-title-2")
                .title("Title 2")
                .author("Author 2")
                .stars(5)
                .price(new BigDecimal("3.99"))
                .favorite(true)
                .imageUrl("image/2");

        BookDtoBuilder blankPropertiesDto = new BookDtoBuilder()
                .title("")
                .author("")
                .stars(2)
                .price(new BigDecimal("2.99"))
                .favorite(false)
                .imageUrl("image/1");

        BookDtoBuilder noUrlIdDto = new BookDtoBuilder()
                .title("Title 1")
                .author("Author 1")
                .stars(2)
                .price(new BigDecimal("2.99"))
                .favorite(false)
                .imageUrl("image/1");

        return List.of(bookDto1, bookDto2, blankPropertiesDto, noUrlIdDto);
    }

    public static List<String> getResponses() {
        return List.of(RESPONSE_1, RESPONSE_2, RESPONSE_3, RESPONSE_4, RESPONSE_5, RESPONSE_6);
    }
}
