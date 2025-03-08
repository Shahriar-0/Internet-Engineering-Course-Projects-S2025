// package cli.command;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertTrue;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.Mockito.mock;
// import static org.mockito.Mockito.when;

// import application.dtos.AddUserDto;
// import application.exceptions.BaseException;
// import application.result.Result;
// import application.services.UserService;
// import cli.response.Response;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import domain.entities.User;
// import domain.valueobjects.Address;
// import java.io.IOException;
// import java.io.InputStream;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// public class AddUserCommandTest {

// 	private UserService userService;
// 	private ObjectMapper objectMapper;

// 	@BeforeEach
// 	public void setup() {
// 		userService = mock(UserService.class);
// 		objectMapper = new ObjectMapper();
// 	}

// 	@Test
// 	public void testAddUserSuccess() throws IOException {
// 		String inputPath = "commands/input/add_user.json";
// 		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(inputPath);
// 		AddUserDto addUserDto = objectMapper.readValue(inputStream, AddUserDto.class);

// 		String outputPath = "commands/output/add_user.json";
// 		InputStream outputStream = getClass().getClassLoader().getResourceAsStream(outputPath);
// 		Response expectedResponse = objectMapper.readValue(outputStream, Response.class);

// 		AddUserDto mockedUser = mock(AddUserDto.class);
//         when(mockedUser.username()).thenReturn(addUserDto.username());
//         when(mockedUser.address()).thenReturn(addUserDto.address());
//         when(mockedUser.email()).thenReturn(addUserDto.email());
//         when(mockedUser.password()).thenReturn(addUserDto.password());
//         when(mockedUser.role()).thenReturn(addUserDto.role());

// 		when(userService.addUser(any(AddUserDto.class))).thenReturn(Result.successOf(mockedUser));

// 		AddUserCommand command = new AddUserCommand(addUserDto, userService);
// 		Response actualResponse = command.execute();

// 		assertTrue(actualResponse.success());
// 		assertEquals(expectedResponse.message(), actualResponse.message());
// 		assertEquals(expectedResponse.data(), actualResponse.data());
// 	}

// 	@Test
// 	public void testAddUserFailure() throws IOException {
// 		String inputPath = "commands/input/add_user.json";
// 		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(inputPath);
// 		AddUserDto addUserDto = objectMapper.readValue(inputStream, AddUserDto.class);

// 		// Mock UserService to return failure
// 		String errorMessage = "User already exists";
// 		when(userService.addUser(any(User.class))).thenReturn(Result.failureOf(new BaseException(errorMessage)));

// 		AddUserCommand command = new AddUserCommand(addUserDto, userService);
// 		Response actualResponse = command.execute();

// 		assertEquals(false, actualResponse.success());
// 		assertEquals(errorMessage, actualResponse.message());
// 		assertEquals(null, actualResponse.data());
// 	}
// }
