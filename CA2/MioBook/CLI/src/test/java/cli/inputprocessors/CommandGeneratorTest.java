package cli.inputprocessors;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import application.services.AdminService;
import application.services.UserService;
import cli.command.AddUserCommand;
import cli.command.IBaseCommand;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import com.fasterxml.jackson.core.JsonParseException;

public class CommandGeneratorTest {

	private CommandGenerator commandGenerator;
	private UserService userService;
	private AdminService adminService;

	@BeforeEach
	public void setup() {
		userService = mock(UserService.class);
		adminService = mock(AdminService.class);

		commandGenerator = new CommandGenerator(userService, adminService);
	}

    private static Stream<String> provideCommandFiles() throws IOException {
        java.net.URL url = CommandGeneratorTest.class.getClassLoader().getResource("commands/input");
        if (url == null) {
            throw new IllegalStateException("Cannot find resource directory");
        }

        try {
            Path inputDir = Paths.get(url.toURI()); // Properly handle the URL
            return Files.list(inputDir)
                .filter(p -> p.toString().endsWith(".json"))
                .map(p -> {
                    try {
                        return Files.readString(p);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
        } catch (Exception e) {
            throw new IOException("Failed to load test command files", e);
        }
    }


	@ParameterizedTest
	@MethodSource("provideCommandFiles")
	public void testCommandParsing(String commandJson) throws IOException {
		// Extract command name from the JSON to verify correct command type
		if (!commandJson.contains("add_user")) {
			return; // Skip non-AddUser commands for now
		}

		IBaseCommand command = commandGenerator.generateCommand(commandJson);

		assertNotNull(command);
		assertTrue(command instanceof AddUserCommand);
	}

	@Test
	public void testInvalidJson() {
		String invalidJson = "add_user {invalid json}";

		Exception exception = assertThrows(
			JsonParseException.class,
			() -> {
				commandGenerator.generateCommand(invalidJson);
			}
		);

		assertTrue(exception.getMessage().contains("Unexpected character"));
	}

	@Test
	public void testUnknownCommand() {
		String unknownCommandJson = "{\"unknown_command\": {\"data\": \"value\"}}";

		Exception exception = assertThrows(
			RuntimeException.class,
			() -> {
				commandGenerator.generateCommand(unknownCommandJson);
			}
		);

		assertTrue(exception.getMessage().contains("No enum constant cli.command.CommandType."));
	}
}
