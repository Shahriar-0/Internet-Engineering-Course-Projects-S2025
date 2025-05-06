package webapi.controllers;

import application.exceptions.businessexceptions.authorexceptions.AuthorDoesNotExists;
import application.exceptions.businessexceptions.bookexceptions.BookAlreadyExists;
import application.exceptions.businessexceptions.bookexceptions.BookDoesntExist;
import application.exceptions.businessexceptions.userexceptions.BookIsNotAccessible;
import application.repositories.IAuthorRepository;
import application.repositories.IBookRepository;
import application.repositories.IUserRepository;
import application.usecase.admin.book.AddBook;
import application.usecase.customer.book.AddReview;
import application.usecase.user.book.GetBookReviews;
import domain.entities.author.Author;
import domain.entities.book.Book;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;

@SpringBootTest
class BookControllerTest {

    static final List<String> BOOK2_GENRES = List.of("Test Genre1", "Test Genre2");

    @Autowired
    BookController bookController;
    @Autowired
    UseCaseService useCaseService;

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

        Author author = authorRepository.save(AuthorFixtureUtil.author(0, admin));

        Book book1 = bookRepository.save(BookFixtureUtil.book(0, admin, author));

        Book book2 = BookFixtureUtil.book(1, admin, author);
        book2.setGenres(BOOK2_GENRES);
        bookRepository.save(book2);

        bookRepository.upsertReview(ReviewFixtureUtil.review(0, book1, customer1), book1, customer1);
        bookRepository.upsertReview(ReviewFixtureUtil.review(1, book1, customer2), book1, customer2);
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
        String title = BookFixtureUtil.title(0);
        GetBookReviews.ReviewFilter filter = new GetBookReviews.ReviewFilter(1, 10);
        PageView<BookReviewsView> page = bookController.getBookReviews(title, filter).getBody().data();

        List<BookReviewsView> expectedReviews = new ArrayList<>();
        expectedReviews.add(ReviewFixtureUtil.view(0, CustomerFixtureUtil.name(0)));
        expectedReviews.add(ReviewFixtureUtil.view(1, CustomerFixtureUtil.name(1)));
        PageView<BookReviewsView> expectedPage = new PageView<>(1, 10, 1, 2L, expectedReviews);

        assertThat(page).isEqualTo(expectedPage);
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

    @Test
    @Disabled
    void searchBook_Scenarios() {
        //TODO: Add search book test scenarios after the repository method completed
    }

    @Test
    void addReview_CustomerDidNotAddReviewBefore_AddCorrectly() {
        //TODO: Add this scenario when adding buying book rewrote
    }

    @Test
    void addReview_CustomerAddedReviewBefore_ReplaceWithOldOne() {
        //TODO: Add this scenario when adding buying book rewrote
    }

    @Test
    void addReview_BookTitleDoesNotExist_ThrowsException() {
        customerLoggedIn(0);
        String title = "Title that does not exist";
        AddReview.AddReviewData data = ReviewFixtureUtil.addReviewData(3);

        assertThatThrownBy(() -> bookController.addReview(data, title))
            .isInstanceOf(BookDoesntExist.class)
            .hasMessage("Book with title '" + title + "' does not exist!");
    }

    @Test
    void addReview_CustomerDoesNotHaveAccess_ThrowsException() {
        customerLoggedIn(1);
        AddReview.AddReviewData data = ReviewFixtureUtil.addReviewData(3);

        assertThatThrownBy(() -> bookController.addReview(data, BookFixtureUtil.title(1)))
            .isInstanceOf(BookIsNotAccessible.class)
            .hasMessage("Book '" + BookFixtureUtil.title(1) + "' is not accessible!");
    }

    @Test
    void getGenres_EverythingIsOk_ReturnsGenreList() {
        List<String> genres = bookController.getGenres().getBody().data();

        List<String> expectedGenres = new ArrayList<>();
        expectedGenres.addAll(BookFixtureUtil.GENRES);
        expectedGenres.addAll(BOOK2_GENRES);
        expectedGenres.sort(String::compareTo);

        assertThat(genres).isEqualTo(expectedGenres);
    }

    private void adminLoggedIn() {
        AuthenticationService mockedAuthenticationService = mock(AuthenticationService.class);
        when(mockedAuthenticationService.getUser())
            .thenReturn(userRepository.findByUsername(AdminFixtureUtil.name(0)).get());

        bookController = new BookController(useCaseService, mockedAuthenticationService);
    }

    private void customerLoggedIn(int index) {
        AuthenticationService mockedAuthenticationService = mock(AuthenticationService.class);
        when(mockedAuthenticationService.getUser())
            .thenReturn(userRepository.findByUsername(CustomerFixtureUtil.name(index)).get());

        bookController = new BookController(useCaseService, mockedAuthenticationService);
    }
}
