package com.bookstore.repository;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

import com.bookstore.model.Book;
import com.bookstore.model.BookBuilder;
import com.bookstore.model.Tag;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@ContextConfiguration(classes = TestConfig.class)
class BookRepositoryTest {

    @Autowired private BookRepository bookRepository;

    @Autowired private TagRepository tagRepository;

    private List<Book> savedBooks;

    @BeforeEach
    void setUp() {

        Tag tag1 = new Tag("tag1");
        Tag tag2 = new Tag("tag2");
        Tag tag3 = new Tag("tag3");
        List<Tag> tags = List.of(tag1, tag2, tag3);

        tagRepository.saveAll(tags);

        Book book1 =
                new BookBuilder()
                        .urlId("Author 1", "Test 1")
                        .title("Test 1")
                        .author("Author 1")
                        .stars(1)
                        .price(new BigDecimal("1.99"))
                        .favorite(false)
                        .imageUrl("test/1")
                        .tags(new ArrayList<>())
                        .build();
        Book book2 =
                new BookBuilder()
                        .urlId("Author 2", "Test 2")
                        .title("Test 2")
                        .author("Author 2")
                        .stars(2)
                        .price(new BigDecimal("2.99"))
                        .favorite(true)
                        .imageUrl("test/2")
                        .tags(new ArrayList<>())
                        .build();
        Book book3 =
                new BookBuilder()
                        .urlId("Author 3", "Test 3")
                        .title("Test 3")
                        .author("Author 3")
                        .stars(3)
                        .price(new BigDecimal("3.99"))
                        .favorite(false)
                        .imageUrl("test/3")
                        .tags(new ArrayList<>())
                        .build();
        Book book4 =
                new BookBuilder()
                        .urlId("Author 4", "Test 4")
                        .title("Test 4")
                        .author("Author 4")
                        .stars(4)
                        .price(new BigDecimal("4.99"))
                        .favorite(false)
                        .imageUrl("test/4")
                        .tags(new ArrayList<>())
                        .build();
        Book book5 =
                new BookBuilder()
                        .urlId("Author 5", "Test 5")
                        .title("Test 5")
                        .author("Author 5")
                        .stars(5)
                        .price(new BigDecimal("5.99"))
                        .favorite(true)
                        .imageUrl("test/5")
                        .tags(new ArrayList<>())
                        .build();
        List<Book> booksToSave = List.of(book1, book2, book3, book4, book5);

        book1.getTags().add(tag1);
        book1.getTags().add(tag2);
        book1.getTags().add(tag3);
        book4.getTags().add(tag1);
        book4.getTags().add(tag3);
        book5.getTags().add(tag2);
        savedBooks = bookRepository.saveAll(booksToSave);
    }

    @Test
    @DisplayName("Should throw exception when trying to add duplicated urlId")
    void shouldThrowExceptionWhenTryingToAddDuplicateUrlId() {
        assertThatThrownBy(
                        () -> bookRepository.save(new Book("author-1-test-1", "Title 1", "Author 1")))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("Should return empty list when db empty")
    void findAllBooksWhenDbIsEmpty() {
        bookRepository.deleteAll();

        List<Book> allBooks = bookRepository.findAllBooks();

        assertThat(allBooks).isEmpty();
    }

    @Test
    @DisplayName("Should find all books when books exist")
    void findAllBooks() {
        List<Book> allBooks = bookRepository.findAllBooks();

        assertThat(allBooks).hasSameElementsAs(savedBooks);
    }

    @Test
    @DisplayName("Should return empty optional when id does not exist")
    void findByUrlIdWhenIdDoesNotExist() {
        Optional<Book> byUrlId = bookRepository.findByUrlId("some-id");

        assertThat(byUrlId).isEmpty();
    }

    @Test
    @DisplayName("Should find a book by urlId when id exists in db")
    void findByUrlIdWhenIdExistsInDb() {
        Book expected = savedBooks.get(0);

        Optional<Book> byUrlId = bookRepository.findByUrlId(expected.getUrlId());

        assertThat(byUrlId.orElseThrow()).isEqualTo(expected);
    }

    @Test
    @DisplayName("Should return 0 if id does not exist")
    void deleteByUrlIdWhenIdDoesNotExist() {
        int deletedRows = bookRepository.deleteByUrlId("some-id");

        assertThat(deletedRows).isEqualTo(0);
    }

    @Test
    @DisplayName("Should return 1 if id does exist")
    void deleteByUrlIdWhenIdExists() {
        int deletedRows = bookRepository.deleteByUrlId("some-id");

        assertThat(deletedRows).isEqualTo(0);
    }

    @Test
    @DisplayName("Should return empty list when specified tag name does not exist")
    void findByTagNameWhenTagNameDoesNotExist() {
        List<Book> found = bookRepository.findByTagName("something");

        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should return a list of books with a specific tag")
    void findAllBooksWithSpecificTag() {
        List<Book> expected = List.of(savedBooks.get(0), savedBooks.get(3));

        List<Book> found = bookRepository.findByTagName("tag1");

        assertThat(found).hasSameElementsAs(expected);
    }

    @Test
    @DisplayName("Should return empty list when searched phrase does not exist in any book's urlId")
    void findByUrlIdContainingPhraseWhenPhraseDoesNotExist() {
        List<Book> found = bookRepository.findByUrlIdContainingPhrase("random");

        assertThat(found).isEmpty();
    }

    @Test
    @DisplayName("Should return correct list of books when searched phrase exist in their urlId")
    void findByUrlIdContainingPhraseWhenPhraseExist() {
        List<Book> found = bookRepository.findByUrlIdContainingPhrase("author");

        assertThat(found).hasSameElementsAs(savedBooks);
    }
}
