package cli.inputprocessors;

import application.services.AdminService;
import application.services.UserService;
import cli.command.AddAuthorCommand;
import cli.command.AddBookCommand;
import cli.command.AddUserCommand;
import cli.command.CommandType;
import cli.command.IBaseCommand;
import cli.dtos.AddAuthorDto;
import cli.dtos.AddBookDto;
import cli.dtos.AddUserDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommandGenerator {

	private final ObjectMapper objectMapper = new ObjectMapper();
	private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
	private static final Validator validator = factory.getValidator();


	private final UserService userService;
	private final AdminService adminService;

	public IBaseCommand generateCommand(String input) throws JsonProcessingException, IllegalArgumentException {
		String jsonString = input.substring(input.indexOf(" ") + 1);

		return switch (CommandType.valueOf(input.split(" ")[0].toUpperCase())) {
			case ADD_USER -> {
				AddUserDto dto = objectMapper.readValue(jsonString, AddUserDto.class);
				validate(dto);
				yield new AddUserCommand(dto, userService);
			}
			case ADD_AUTHOR -> {
				AddAuthorDto dto = objectMapper.readValue(jsonString, AddAuthorDto.class);
				validate(dto);
				yield new AddAuthorCommand(dto, adminService);
			}
			case ADD_BOOK -> {
				AddBookDto dto = objectMapper.readValue(jsonString, AddBookDto.class);
				validate(dto);
				yield new AddBookCommand(dto, adminService);
			}
		};
	}

	private static <T> void validate(T object) {
		Set<ConstraintViolation<T>> violations = validator.validate(object);

		if (!violations.isEmpty()) {
			StringBuilder errorMessage = new StringBuilder("Validation failed:\n");
			for (ConstraintViolation<T> violation : violations) {
				errorMessage.append(violation.getPropertyPath()).append(": ")
							.append(violation.getMessage()).append("\n");
			}
			throw new IllegalArgumentException(errorMessage.toString());
		}
	}
}
