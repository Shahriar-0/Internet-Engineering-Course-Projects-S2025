package cli.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import application.exceptions.BaseException;
import application.result.Result;
import application.services.UserService;
import cli.dtos.AddUserDto;
import cli.response.Response;
import domain.entities.User;
import domain.valueobjects.Address;

public class AddUserCommandTest {

    private UserService userService;
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        userService = mock(UserService.class);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAddUserSuccess() throws IOException {
        String inputPath = "commands/input/add_user.json";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(inputPath);
        AddUserDto addUserDto = objectMapper.readValue(inputStream, AddUserDto.class);

        String outputPath = "commands/output/add_user.json";
        InputStream outputStream = getClass().getClassLoader().getResourceAsStream(outputPath);
        Response expectedResponse = objectMapper.readValue(outputStream, Response.class);

        User mockedUser = User.builder()
                .key(addUserDto.username())
                .password(addUserDto.password())
                .email(addUserDto.email())
                .role(User.Role.Customer)
                .address(new Address(addUserDto.address().country(), addUserDto.address().city()))
                .build();

        when(userService.addUser(any(User.class))).thenReturn(Result.successOf(mockedUser));

        AddUserCommand command = new AddUserCommand(addUserDto, userService);
        Response actualResponse = command.execute();

        assertTrue(actualResponse.success());
        assertEquals(expectedResponse.message(), actualResponse.message());
        assertEquals(expectedResponse.data(), actualResponse.data());
    }

    @Test
    public void testAddUserFailure() throws IOException {
        String inputPath = "commands/input/add_user.json";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(inputPath);
        AddUserDto addUserDto = objectMapper.readValue(inputStream, AddUserDto.class);

        // Mock UserService to return failure
        String errorMessage = "User already exists";
        when(userService.addUser(any(User.class))).thenReturn(Result.failureOf(new BaseException(errorMessage)));

        AddUserCommand command = new AddUserCommand(addUserDto, userService);
        Response actualResponse = command.execute();

        assertEquals(false, actualResponse.success());
        assertEquals(errorMessage, actualResponse.message());
        assertEquals(null, actualResponse.data());
    }

    @Test
    public void testInvalidRole() throws IOException {
        AddUserDto invalidRoleDto = objectMapper.readValue(
            getClass().getClassLoader().getResourceAsStream("commands/input/add_user.json"),
            AddUserDto.class
        );

        // Modify to have invalid role via reflection or creating a new object
        AddUserDto modifiedDto = new AddUserDto(
            "invalid_role",
            invalidRoleDto.username(),
            invalidRoleDto.password(),
            invalidRoleDto.email(),
            invalidRoleDto.address()
        );

        AddUserCommand command = new AddUserCommand(modifiedDto, userService);


        Exception exception = assertThrows(IllegalArgumentException.class, command::execute);
        assertTrue(exception.getMessage().contains("Invalid user role"));
    }
}