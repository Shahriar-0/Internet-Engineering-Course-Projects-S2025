package webapi.controllers;

import application.exceptions.businessexceptions.userexceptions.UserNotFound;
import application.repositories.IUserRepository;
import application.usecase.user.account.CreateAccount;
import domain.entities.user.Admin;
import domain.entities.user.Customer;
import domain.entities.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import webapi.fixture.CustomerFixtureUtil;
import webapi.fixture.AdminFixtureUtil;
import webapi.response.Response;
import webapi.views.user.UserView;

import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CREATED;

@SpringBootTest
class UserControllerTest {

    @Autowired
    UserController userController;

    @Autowired
    IUserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.save(CustomerFixtureUtil.customer(0));
        userRepository.save(AdminFixtureUtil.admin(0));
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void getUser_UserIsCustomer_ReturnsCorrectUser() {
        UserView userView = userController.getUser(CustomerFixtureUtil.name(0)).getBody().data();
        assertThat(userView).isEqualTo(CustomerFixtureUtil.view(0));
    }

    @Test
    void getUser_UserIsAdmin_ReturnsCorrectUser() {
        UserView userView = userController.getUser(AdminFixtureUtil.name(0)).getBody().data();
        assertThat(userView).isEqualTo(AdminFixtureUtil.view(0));
    }

    @Test
    void getUser_UsernameDoesNotExists_ThrowsException() {
        String username = "Non important username";

        assertThatThrownBy(() -> userController.getUser(username))
            .isInstanceOf(UserNotFound.class)
            .hasMessage("User with username " + username + " not found");
    }

    @Test
    void addUser_ValidCustomerUser_AddCorrectly() {
        Response<?> response = userController.addUser(CustomerFixtureUtil.addUserData(1));
        assertThat(response.getStatusCode()).isEqualTo(CREATED);

        Optional<User> user = userRepository.findByUsername(CustomerFixtureUtil.name(1));
        assertThat(user).isPresent();
        CustomerFixtureUtil.assertion((Customer) user.get(), CustomerFixtureUtil.customer(1));
    }

    @Test
    void addUser_ValidAdminUser_AddCorrectly() {
        Response<?> response = userController.addUser(AdminFixtureUtil.addUserData(1));
        assertThat(response.getStatusCode()).isEqualTo(CREATED);

        Optional<User> user = userRepository.findByUsername(AdminFixtureUtil.name(1));
        assertThat(user).isPresent();
        AdminFixtureUtil.assertion((Admin) user.get(), AdminFixtureUtil.admin(1));
    }

    @Test
    void addUser_UsernameIsAlreadyExists_ReturnsBadRequestWithProperErrorMessage() {
        CreateAccount.AddUserData data = CustomerFixtureUtil.addUserData(0);
        CreateAccount.AddUserData dataWithOnlyDuplicateUsername = new CreateAccount.AddUserData(
            data.role(), data.username(), data.password(), "random@gmail.com", data.address()
        );

        Response<?> response = userController.addUser(dataWithOnlyDuplicateUsername);
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);

        Map<String, String> errors = (Map<String, String>) response.getBody().data();
        assertThat(errors.containsKey("username")).isTrue();
    }

    @Test
    void addUser_EmailIsAlreadyExists_ReturnsBadRequestWithProperErrorMessage() {
        CreateAccount.AddUserData data = AdminFixtureUtil.addUserData(0);
        CreateAccount.AddUserData dataWithOnlyDuplicateEmail = new CreateAccount.AddUserData(
            data.role(), "random username", data.password(), data.email(), data.address()
        );

        Response<?> response = userController.addUser(dataWithOnlyDuplicateEmail);
        assertThat(response.getStatusCode()).isEqualTo(BAD_REQUEST);

        Map<String, String> errors = (Map<String, String>) response.getBody().data();
        assertThat(errors.containsKey("email")).isTrue();
    }
}
