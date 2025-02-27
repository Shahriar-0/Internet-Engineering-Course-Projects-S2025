package cli.command;

import application.result.Result;
import application.services.UserService;
import cli.dtos.AddUserDto;
import cli.response.Response;
import domain.entities.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddUserCommand implements IBaseCommand {

	private static final String SUCCESS_MESSAGE = "User added successfully.";

	private final AddUserDto addUserDto;
	private final UserService userService;

	@Override
	public Response execute() {
		Result<User> result = userService.addUser(createUser(addUserDto));
		return new Response(result, SUCCESS_MESSAGE, false);
	}

	private User createUser(AddUserDto dto) {
		return User
			.builder()
			.key(dto.username())
			.address(dto.address())
			.password(dto.password())
			.email(dto.email())
			.role(User.Role.valueOf(dto.role().toUpperCase()))
			.build();
	}
}
