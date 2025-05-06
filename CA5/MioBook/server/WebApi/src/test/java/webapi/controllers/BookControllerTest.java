package webapi.controllers;

import application.exceptions.businessexceptions.authorexceptions.AuthorDoesNotExists;
import application.exceptions.businessexceptions.bookexceptions.BookAlreadyExists;
import application.exceptions.businessexceptions.bookexceptions.BookDoesntExist;
import application.repositories.IAuthorRepository;
import application.repositories.IBookRepository;
import application.repositories.IUserRepository;
import application.usecase.admin.book.AddBook;
import application.usecase.user.book.GetBookReviews;
import domain.entities.author.Author;
import domain.entities.book.Book;
import domain.entities.book.Review;
import domain.entities.user.Admin;
import domain.entities.user.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import webapi.fixture.*;
import webapi.response.Response;
import webapi.services.AuthenticationService;
import webapi.services.UseCaseService;
import webapi.views.book.BookReviewsView;
import webapi.views.book.BookView;
import webapi.views.page.PageView;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;

@SpringBootTest
class BookControllerTest {

    @Autowired
    BookController bookController;
    @Autowired
    UseCaseService useCaseService;
    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    IUserRepository userRepository;
    @Autowired
    IAuthorRepository authorRepository;
    @Autowired
    IBookRepository bookRepository;


    @BeforeEach
    void setup() {
        Admin admin = (Admin) userRepository.save(AdminFixtureUtil.admin(0));
        Customer customer1 = (Customer) userRepository.save(CustomerFixtureUtil.customer(0));
        Customer customer2 = (Customer) userRepository.save(CustomerFixtureUtil.customer(1));

        Author author = AuthorFixtureUtil.author(0);
        author.setAdmin(admin);
        author = authorRepository.save(author);

        Book book1 = BookFixtureUtil.book(0);
        book1.setAdmin(admin);
        book1.setAuthor(author);
        book1 = bookRepository.save(book1);

        Book book2 = BookFixtureUtil.book(1);
        book2.setAdmin(admin);
        book2.setAuthor(author);
        bookRepository.save(book2);

        Review review1 = ReviewFixtureUtil.review(0, book1, customer1);
        Review review2 = ReviewFixtureUtil.review(1, book1, customer2);
        //TODO: Add these reviews to repo after adding review was rewrote
    }

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void addBook_ValidBook_AddCorrectly() {
        adminLoggedIn();
        AddBook.AddBookData data = BookFixtureUtil.addBookData(2);
        data.setAuthor(AuthorFixtureUtil.name(0));

        Response<?> response = bookController.addBook(data);
        assertThat(response.getStatusCode()).isEqualTo(CREATED);

        Optional<Book> book = bookRepository.findByTitle(data.getTitle());
        assertThat(book).isPresent();

        Book expectedBook = BookFixtureUtil.book(2);
        expectedBook.setDateAdded(book.get().getDateAdded());
        BookFixtureUtil.assertion(book.get(), expectedBook);
    }

    @Test
    void addBook_AuthorDoesNotExist_ThrowsException() {
        String authorName = "Author name that does not exist";

        adminLoggedIn();
        AddBook.AddBookData data = BookFixtureUtil.addBookData(1);
        data.setAuthor(authorName);

        assertThatThrownBy(() -> bookController.addBook(data))
            .isInstanceOf(AuthorDoesNotExists.class)
            .hasMessage("Author with username '" + authorName + "' does not exist!");
    }

    @Test
    void addBook_BookTitleAlreadyExists_ThrowsException() {
        adminLoggedIn();
        AddBook.AddBookData data = BookFixtureUtil.addBookData(1);
        data.setAuthor(AuthorFixtureUtil.name(0));
        data.setTitle(BookFixtureUtil.title(0));

        assertThatThrownBy(() -> bookController.addBook(data))
            .isInstanceOf(BookAlreadyExists.class)
            .hasMessage("Book with title '" + data.getTitle() + "' already exists!");
    }

    @Test
    void getBook_BookExists_ReturnsCorrectBook() {
        BookView book = bookController.getBook(BookFixtureUtil.title(0)).getBody().data();

        BookView expectedView = BookFixtureUtil.view(0);
        expectedView.setAuthor(AuthorFixtureUtil.name(0));
        expectedView.setAverageRating(0.0f);

        assertThat(book).isEqualTo(expectedView);
    }

    @Test
    void getBook_BookDoesNotExist_ThrowsException() {
        String title = "Title that does not exist";

        assertThatThrownBy(() -> bookController.getBook(title))
            .isInstanceOf(BookDoesntExist.class)
            .hasMessage("Book with title '" + title + "' does not exist!");
    }

    @Test
    @Disabled
    void getBookContent_Scenarios() {
        //TODO: Add getBookContent test scenarios after rewriting its use case
    }

    @Test
    @Disabled
    void getBookReviews_BookHasReviews_ReturnsReviewsInPage() {
        //TODO: Add this scenario after adding reviews to fixture
    }

    @Test
    void getBookReviews_BookHasNotAnyReviews_ReturnsEmptyPage() {
        String title = BookFixtureUtil.title(1);
        GetBookReviews.ReviewFilter filter = new GetBookReviews.ReviewFilter(1, 10);
        PageView<BookReviewsView> page = bookController.getBookReviews(title, filter).getBody().data();

        PageView<BookReviewsView> expectedPage = new PageView<>(1, 10, 0, 0L, List.of());

        assertThat(page).isEqualTo(expectedPage);
    }

    @Test
    void getBookReviews_BookDoesNotExist_ThrowsException() {
        String title = "Title that does not exist";
        GetBookReviews.ReviewFilter filter = new GetBookReviews.ReviewFilter(1, 10);

        assertThatThrownBy(() -> bookController.getBookReviews(title, filter))
            .isInstanceOf(BookDoesntExist.class)
            .hasMessage("Book with title '" + title + "' does not exist!");
    }

    private void adminLoggedIn() {
        AuthenticationService mockedAuthenticationService = mock(AuthenticationService.class);
        when(mockedAuthenticationService.getUser())
            .thenReturn(userRepository.findByUsername(AdminFixtureUtil.name(0)).get());

        bookController = new BookController(useCaseService, mockedAuthenticationService);
    }

    private void customerLoggedIn() {
        AuthenticationService mockedAuthenticationService = mock(authenticationService);
        when(mockedAuthenticationService.getUser())
            .thenReturn(userRepository.findByUsername(CustomerFixtureUtil.name(0)).get());

        bookController = new BookController(useCaseService, mockedAuthenticationService);
    }
}
