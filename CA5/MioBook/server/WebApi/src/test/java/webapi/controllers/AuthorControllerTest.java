package webapi.controllers;

import application.repositories.IAuthorRepository;
import application.repositories.IBookRepository;
import application.repositories.IUserRepository;
import domain.entities.author.Author;
import domain.entities.user.Admin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import webapi.fixture.AdminFixtureUtil;
import webapi.fixture.AuthorFixtureUtil;
import webapi.fixture.BookFixtureUtil;
import webapi.services.AuthenticationService;
import webapi.services.UseCaseService;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthorControllerTest {

    @Autowired
    AuthorController authorController;
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

        Author author = authorRepository.save(AuthorFixtureUtil.author(0, admin));
        authorRepository.save(AuthorFixtureUtil.author(1, admin));

        bookRepository.save(BookFixtureUtil.book(0, admin, author));
        bookRepository.save(BookFixtureUtil.book(1, admin, author));
        bookRepository.save(BookFixtureUtil.book(2, admin, author));
        bookRepository.save(BookFixtureUtil.book(3, admin, author));
    }

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        userRepository.deleteAll();
    }

    private void adminLoggedIn() {
        AuthenticationService mockedAuthenticationService = mock(AuthenticationService.class);
        when(mockedAuthenticationService.getUser())
            .thenReturn(userRepository.findByUsername(AdminFixtureUtil.name(0)).get());

        authorController = new AuthorController(useCaseService, mockedAuthenticationService);
    }

}
