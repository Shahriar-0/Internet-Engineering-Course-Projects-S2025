package webapi.controllers;

import application.exceptions.businessexceptions.userexceptions.InvalidAccess;
import application.repositories.IUserRepository;
import application.usecase.user.account.Login;
import domain.entities.user.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import webapi.exceptions.AuthenticationException;
import webapi.fixture.AdminFixtureUtil;
import webapi.fixture.BookFixtureUtil;
import webapi.fixture.CustomerFixtureUtil;
import webapi.response.Response;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@SpringBootTest
class AuthenticationControllerTest {

    static final String WRONG_USERNAME_OR_PASSWORD_MESSAGE = "Username/Email or Password is not correct";

    @Autowired
    AuthenticationController authenticationController;
    @Autowired
    BookController bookController;

    @Autowired
    IUserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.save(AdminFixtureUtil.admin(0));
        userRepository.save(CustomerFixtureUtil.customer(0));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void login_CustomerUser_ReturnsItsRole() {
        String role = (String) authenticationController.login(CustomerFixtureUtil.loginData(0)).getBody().data();
        assertThat(role).isEqualTo(Role.CUSTOMER.getValue());
        authenticationController.logout();
    }

    @Test
    void login_AdminUser_ReturnsItsRole() {
        String role = (String) authenticationController.login(AdminFixtureUtil.loginData(0)).getBody().data();
        assertThat(role).isEqualTo(Role.ADMIN.getValue());
        authenticationController.logout();
    }

    @Test
    void login_UsernameDoesNotExist_ReturnsUnauthorizedStatusWithProperMessage() {
        Login.LoginData data = CustomerFixtureUtil.loginData(0);
        data.setUsername("Does not exist");

        Response<?> response = authenticationController.login(data);
        assertThat(response.getStatusCode()).isEqualTo(UNAUTHORIZED);

        assertThat(response.getBody().message()).isEqualTo(WRONG_USERNAME_OR_PASSWORD_MESSAGE);
    }

    @Test
    void login_PasswordIsWrong_ReturnsUnauthorizedStatusWithProperMessage() {
        Login.LoginData data = CustomerFixtureUtil.loginData(0);
        data.setPassword("Wrong password");

        Response<?> response = authenticationController.login(data);
        assertThat(response.getStatusCode()).isEqualTo(UNAUTHORIZED);

        assertThat(response.getBody().message()).isEqualTo(WRONG_USERNAME_OR_PASSWORD_MESSAGE);
    }

    @Test
    void login_SomeOneLoggedInBefore_ThrowsException() {
        authenticationController.login(AdminFixtureUtil.loginData(0));

        assertThatThrownBy(() -> authenticationController.login(AdminFixtureUtil.loginData(0)))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("Some one logged in");

        authenticationController.logout();
    }

    @Test
    void logout_EverythingIsOk_ReturnsOkStatusAndProperMessage() {
        authenticationController.login(AdminFixtureUtil.loginData(0));

        Response<?> response = authenticationController.logout();
        assertThat(response.getStatusCode()).isEqualTo(OK);
        assertThat(response.getBody().message()).isEqualTo("User logged out successfully");
    }

    @Test
    void logout_NoOneLoggedIn_ThrowsException() {
        assertThatThrownBy(() -> authenticationController.logout())
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("No one logged in");
    }

    @Test
    void invalidAccessTest_LoggedOutUserAccessCustomerAccessibleEndPoint_ThrowsException() {
        assertThatThrownBy(() -> bookController.getBookContent("title"))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("No one logged in");
    }

    @Test
    void invalidAccessTest_LoggedOutUserAccessAdminAccessibleEndPoint_ThrowsException() {
        assertThatThrownBy(() -> bookController.addBook(BookFixtureUtil.addBookData(0)))
            .isInstanceOf(AuthenticationException.class)
            .hasMessage("No one logged in");
    }

    @Test
    void invalidAccessTest_CustomerUserAccessAdminAccessibleEndPoint_ThrowsException() {
        authenticationController.login(CustomerFixtureUtil.loginData(0));

        assertThatThrownBy(() -> bookController.addBook(BookFixtureUtil.addBookData(0)))
            .isInstanceOf(InvalidAccess.class)
            .hasMessage("The role 'customer' has not appear in white list: {'admin'} !");

        authenticationController.logout();
    }

    @Test
    void invalidAccessTest_AdminUserAccessCustomerAccessibleEndPoint_ThrowsException() {
        authenticationController.login(AdminFixtureUtil.loginData(0));

        assertThatThrownBy(() -> bookController.getBookContent("title"))
            .isInstanceOf(InvalidAccess.class)
            .hasMessage("The role 'admin' has not appear in white list: {'customer'} !");

        authenticationController.logout();
    }
}
