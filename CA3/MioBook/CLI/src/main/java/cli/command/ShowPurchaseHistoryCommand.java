package cli.command;

import application.dtos.ShowPurchaseHistoryDto;
import application.result.Result;
import application.services.UserService;
import cli.response.Response;
import domain.valueobjects.PurchaseHistory;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShowPurchaseHistoryCommand implements IBaseCommand {

    private static final String SUCCESS_MESSAGE = "Purchase history retrieved successfully.";

    private final ShowPurchaseHistoryDto showPurchaseHistoryDto;
    private final UserService userService;

    @Override
    public Response execute() {
        Result<PurchaseHistory> result = userService.showPurchaseHistory(showPurchaseHistoryDto);
        return new Response(result, SUCCESS_MESSAGE, true);
    }
}
