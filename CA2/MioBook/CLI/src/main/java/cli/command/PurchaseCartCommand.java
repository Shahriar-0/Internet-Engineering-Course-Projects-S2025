package cli.command;

import application.dtos.PurchaseCartDto;
import application.result.Result;
import application.services.UserService;
import cli.response.Response;
import domain.valueobjects.PurchasedCart;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class PurchaseCartCommand implements IBaseCommand {

    private static final String SUCCESS_MESSAGE = "Purchase completed successfully.";

    private final PurchaseCartDto purchaseCartDto;
    private final UserService userService;

    @Override
    public Response execute() {
        Result<PurchasedCart> result = userService.purchaseCart(purchaseCartDto);
        return new Response(result, SUCCESS_MESSAGE, true);
    }
}
