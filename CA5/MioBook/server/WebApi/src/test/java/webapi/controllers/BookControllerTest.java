package webapi.controllers;

import application.repositories.IAuthorRepository;
import application.repositories.IBookRepository;
import application.repositories.IUserRepository;
import domain.entities.author.Author;
import domain.entities.book.Book;
import domain.entities.user.Admin;
import domain.entities.user.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import webapi.fixture.AdminFixtureUtil;
import webapi.fixture.AuthorFixtureUtil;
import webapi.fixture.BookFixtureUtil;
import webapi.fixture.CustomerFixtureUtil;
import webapi.services.AuthenticationService;
import webapi.services.UseCaseService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookControllerTest {

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
        setFixture();
        adminLoggedIn();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        authorRepository.deleteAll();
        bookRepository.deleteAll();
    }

    private void setFixture() {
        Admin admin = AdminFixtureUtil.admin(0);
        Customer customer = CustomerFixtureUtil.customer(0);
        Author author = AuthorFixtureUtil.author(0);
        author.setAdmin(admin);
        Book book = BookFixtureUtil.book(0);
        book.setAdmin(admin);
        book.setAuthor(author);

        userRepository.save(admin);
        userRepository.save(customer);
        authorRepository.save(author);
        bookRepository.save(book);
    }

    private void adminLoggedIn() {
        AuthenticationService mockedAuthenticationService = mock(authenticationService);
        when(mockedAuthenticationService.getUser()).thenReturn(AdminFixtureUtil.admin(0));
        bookController = new BookController(useCaseService, mockedAuthenticationService);
    }

    private void customerLoggedIn() {
        AuthenticationService mockedAuthenticationService = mock(authenticationService);
        when(mockedAuthenticationService.getUser()).thenReturn(CustomerFixtureUtil.customer(0));
        bookController = new BookController(useCaseService, mockedAuthenticationService);
    }
}
