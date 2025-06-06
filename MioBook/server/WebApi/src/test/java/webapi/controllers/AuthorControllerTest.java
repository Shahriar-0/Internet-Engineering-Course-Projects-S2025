package webapi.controllers;

import application.exceptions.businessexceptions.authorexceptions.AuthorAlreadyExists;
import application.exceptions.businessexceptions.authorexceptions.AuthorDoesNotExists;
import application.repositories.IAuthorRepository;
import application.repositories.IBookRepository;
import application.repositories.IUserRepository;
import application.usecase.admin.author.AddAuthor;
import domain.entities.author.Author;
import domain.entities.user.Admin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import webapi.fixture.AdminFixtureUtil;
import webapi.fixture.AuthorFixtureUtil;
import webapi.fixture.BookFixtureUtil;
import webapi.response.Response;
import webapi.services.AuthenticationService;
import webapi.services.UseCaseService;
import webapi.views.author.AuthorView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;

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

    @Test
    void addAuthor_ValidAuthor_AddCorrectly() {
        adminLoggedIn();
        AddAuthor.AddAuthorData data = AuthorFixtureUtil.addAuthorData(2);

        Response<?> response = authorController.addAuthor(data);
        assertThat(response.getStatusCode()).isEqualTo(CREATED);

        Optional<Author> author = authorRepository.findByName(data.getName());
        assertThat(author).isPresent();

        AuthorFixtureUtil.assertion(author.get(), AuthorFixtureUtil.author(2));
    }

    @Test
    void addAuthor_AuthorNameAlreadyExists_ThrowsException() {
        adminLoggedIn();
        AddAuthor.AddAuthorData data = AuthorFixtureUtil.addAuthorData(0);

        assertThatThrownBy(() -> authorController.addAuthor(data))
            .isInstanceOf(AuthorAlreadyExists.class)
            .hasMessage("Author with username '" + data.getName() + "' already exists!");
    }

    @Test
    void getAuthor_EverythingIsOk_ReturnsCorrectAuthorView() {
        AuthorView view = authorController.getAuthor(AuthorFixtureUtil.name(0)).getBody().data();

        AuthorView expectedView = AuthorFixtureUtil.view(0);
        expectedView.setBookCount(4);

        assertThat(view).isEqualTo(expectedView);
    }

    @Test
    void getAuthor_AuthorNameDoesNotExist_ThrowsException() {
        String authorName = "Author name that does not exist";

        assertThatThrownBy(() -> authorController.getAuthor(authorName))
            .isInstanceOf(AuthorDoesNotExists.class)
            .hasMessage("Author with username '" + authorName + "' does not exist!");
    }

    @Test
    void getAuthors_EverythingIsOk_ReturnsAuthorList() {
        List<AuthorView> views = authorController.getAuthors().getBody().data();

        List<AuthorView> expectedViews = new ArrayList<>();
        expectedViews.add(AuthorFixtureUtil.view(0));
        expectedViews.getFirst().setBookCount(4);
        expectedViews.add(AuthorFixtureUtil.view(1));

        assertThat(views.size()).isEqualTo(2);
        assertThat(views).isEqualTo(expectedViews);
    }

    private void adminLoggedIn() {
        AuthenticationService mockedAuthenticationService = mock(AuthenticationService.class);
        when(mockedAuthenticationService.getUser())
            .thenReturn(userRepository.findByUsername(AdminFixtureUtil.name(0)).get());

        authorController = new AuthorController(useCaseService, mockedAuthenticationService);
    }
}
