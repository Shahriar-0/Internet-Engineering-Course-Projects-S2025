package cli.command;

import application.dtos.AddCartDto;
import application.result.Result;
import application.services.UserService;
import cli.response.Response;
import domain.entities.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddCartCommand implements IBaseCommand {

	private static final String SUCCESS_MESSAGE = "Added book to cart.";

	private final AddCartDto addCartDto;
	private final UserService userService;

	@Override
	public Response execute() {
		Result<User> result = userService.addCart(addCartDto);
		return new Response(result, SUCCESS_MESSAGE, false);
	}
}
