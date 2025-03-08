package cli.command;

import application.dtos.ShowPurchasedBooksDto;
import application.result.Result;
import application.services.UserService;
import cli.response.Response;
import domain.valueobjects.PurchasedBooks;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShowPurchasedBooksCommand implements IBaseCommand {

    private static final String SUCCESS_MESSAGE = "Purchased books retrieved successfully.";

    private final ShowPurchasedBooksDto showPurchasedBooksDto;
    private final UserService userService;

    @Override
    public Response execute() {
        Result<PurchasedBooks> result = userService.showPurchasedBooks(showPurchasedBooksDto);
        return new Response(result, SUCCESS_MESSAGE, true);
    }
}
