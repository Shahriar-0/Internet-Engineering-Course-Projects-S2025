package cli.command;

import application.dtos.ShowUserDetailsDto;
import application.result.Result;
import application.services.UserService;
import cli.response.Response;
import domain.entities.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShowUserDetailsCommand implements IBaseCommand {

    private static final String SUCCESS_MESSAGE = "User details retrieved successfully.";

    private final ShowUserDetailsDto showUserDetailsDto;
    private final UserService userService;

    @Override
    public Response execute() {
        Result<User> userSearchResult = userService.showUserDetails(showUserDetailsDto);
        return new Response(userSearchResult, SUCCESS_MESSAGE, true);
    }
}
