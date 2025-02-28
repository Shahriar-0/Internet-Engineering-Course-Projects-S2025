package cli.command;

import application.dtos.RemoveCartDto;
import application.result.Result;
import application.services.UserService;
import cli.response.Response;
import domain.entities.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RemoveCartCommand implements IBaseCommand {

    private static final String SUCCESS_MESSAGE = "Removed book from cart.";

    private final RemoveCartDto removeCartDto;
    private final UserService userService;

    @Override
    public Response execute() {
        Result<User> result = userService.RemoveCart(removeCartDto);
        return new Response(result, SUCCESS_MESSAGE, false);
    }

}
