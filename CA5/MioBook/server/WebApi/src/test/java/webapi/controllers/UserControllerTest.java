package webapi.controllers;

import application.repositories.IUserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import webapi.fixture.CustomerFixtureUtil;
import webapi.fixture.AdminFixtureUtil;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

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
    void sdlafj() {
        assertThat(true).isTrue();
    }

}