package cli.command;

import application.dtos.AddCreditDto;
import application.result.Result;
import application.services.UserService;
import cli.response.Response;
import domain.entities.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddCreditCommand implements IBaseCommand {

    private static final String SUCCESS_MESSAGE = "Credit added successfully.";

    private final AddCreditDto addCreditDto;
    private final UserService userService;

    @Override
    public Response execute() {
        Result<User> result = userService.addCredit(addCreditDto);
        return new Response(result, SUCCESS_MESSAGE, false);
    }

}
