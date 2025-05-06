package webapi.controllers;

import application.exceptions.businessexceptions.authorexceptions.AuthorDoesNotExists;
import application.exceptions.businessexceptions.bookexceptions.BookAlreadyExists;
import application.exceptions.businessexceptions.bookexceptions.BookDoesntExist;
import application.repositories.IAuthorRepository;
import application.repositories.IBookRepository;
import application.repositories.IUserRepository;
import application.usecase.admin.book.AddBook;
import domain.entities.author.Author;
import domain.entities.book.Book;
import domain.entities.user.Admin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import webapi.fixture.AdminFixtureUtil;
import webapi.fixture.AuthorFixtureUtil;
import webapi.fixture.BookFixtureUtil;
import webapi.fixture.CustomerFixtureUtil;
import webapi.response.Response;
import webapi.services.AuthenticationService;
import webapi.services.UseCaseService;
import webapi.views.book.BookView;

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
        userRepository.save(CustomerFixtureUtil.customer(0));

        Author author = AuthorFixtureUtil.author(0);
        author.setAdmin(admin);
        author = authorRepository.save(author);

        Book book = BookFixtureUtil.book(0);
        book.setAdmin(admin);
        book.setAuthor(author);
        bookRepository.save(book);
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
        AddBook.AddBookData data = BookFixtureUtil.addBookData(1);
        data.setAuthor(AuthorFixtureUtil.name(0));

        Response<?> response = bookController.addBook(data);
        assertThat(response.getStatusCode()).isEqualTo(CREATED);

        Optional<Book> book = bookRepository.findByTitle(BookFixtureUtil.title(1));
        assertThat(book).isPresent();

        Book expectedBook = BookFixtureUtil.book(1);
        expectedBook.setDateAdded(book.get().getDateAdded());
        BookFixtureUtil.assertion(book.get(), expectedBook);
    }

    @Test
    void addBook_AuthorDoesNotExist_ThrowsException() {
        adminLoggedIn();
        AddBook.AddBookData data = BookFixtureUtil.addBookData(1);
        data.setAuthor(AuthorFixtureUtil.name(1));

        assertThatThrownBy(() -> bookController.addBook(data))
            .isInstanceOf(AuthorDoesNotExists.class)
            .hasMessage("Author with username '" + AuthorFixtureUtil.name(1) + "' does not exist!");
    }

    @Test
    void addBook_BookTitleAlreadyExists_ThrowsException() {
        adminLoggedIn();
        AddBook.AddBookData data = BookFixtureUtil.addBookData(1);
        data.setAuthor(AuthorFixtureUtil.name(0));
        data.setTitle(BookFixtureUtil.title(0));

        assertThatThrownBy(() -> bookController.addBook(data))
            .isInstanceOf(BookAlreadyExists.class)
            .hasMessage("Book with title '" + BookFixtureUtil.title(0) + "' already exists!");
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
        assertThatThrownBy(() -> bookController.getBook(BookFixtureUtil.title(1)))
            .isInstanceOf(BookDoesntExist.class)
            .hasMessage("Book with title '" + BookFixtureUtil.title(1) + "' does not exist!");
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
