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
	private static final String CUSTOMER_ROLE = "customer";
	private static final String ADMIN_ROLE = "admin";

	private final AddUserDto addUserDto;
	private final UserService userService;

	@Override
	public Response execute() {
		Result<User> result = userService.addUser(createUser(addUserDto));
		return createResponse(result);
	}

	private Response createResponse(Result<User> result) {
		String message = result.isSuccessful() ? SUCCESS_MESSAGE : result.getException().getMessage();
		return new Response(result.isSuccessful(), message, null);
	}

	private User createUser(AddUserDto dto) {
		return User
			.builder()
			.key(dto.username())
			.address(dto.address())
			.password(dto.password())
			.email(dto.email())
			.role(getRole(dto.role()))
			.build();
	}

	private User.Role getRole(String role) {
		return switch (role) {
			case CUSTOMER_ROLE -> User.Role.Customer;
			case ADMIN_ROLE -> User.Role.Admin;
			default -> throw new IllegalArgumentException("Invalid user role");
		};
	}
}
