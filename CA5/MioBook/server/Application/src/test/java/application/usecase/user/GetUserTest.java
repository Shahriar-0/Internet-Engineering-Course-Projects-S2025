package application.usecase.user;


import application.fixture.AdminFixtureUtil;
import application.fixture.CustomerFixtureUtil;
import application.repositories.IUserRepository;
import application.result.Result;
import domain.entities.user.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetUserTest {

    @Autowired
    private GetUser usecase;

    @Autowired
    private IUserRepository userRepository;

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
    void getCustomerUserWorksCorrectly() {
        Result<User> userResult = usecase.perform(CustomerFixtureUtil.name(0));

        System.out.println(userResult.data());
    }
}