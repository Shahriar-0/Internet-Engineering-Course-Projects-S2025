package cli.command;

import application.dtos.ShowCartDto;
import application.result.Result;
import application.services.UserService;
import cli.response.Response;
import domain.valueobjects.Cart;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShowCartCommand implements IBaseCommand {

    private static final String SUCCESS_MESSAGE = "Buy cart retrieved successfully.";

    private final ShowCartDto showCartDto;
    private final UserService userService;

    @Override
    public Response execute() {
        Result<Cart> cartResult = userService.showCart(showCartDto);
        return new Response(cartResult, SUCCESS_MESSAGE, true);
    }
}
