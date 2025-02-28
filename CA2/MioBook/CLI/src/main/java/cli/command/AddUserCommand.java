package cli.command;

import application.dtos.AddUserDto;
import application.result.Result;
import application.services.UserService;
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
		Result<User> result = userService.addUser(addUserDto);
		return new Response(result, SUCCESS_MESSAGE, false);
	}
}
