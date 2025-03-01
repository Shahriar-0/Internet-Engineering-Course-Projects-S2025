package cli.command;

import application.dtos.ShowAuthorDetailsDto;
import application.result.Result;
import application.services.UserService;
import cli.response.Response;
import domain.entities.Author;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShowAuthorDetailsCommand implements IBaseCommand {

	private static final String SUCCESS_MESSAGE = "Author details retrieved successfully.";

	private final ShowAuthorDetailsDto showAuthorDetailsDto;
	private final UserService userService;

	@Override
	public Response execute() {
		Result<Author> result = userService.showAuthorDetails(showAuthorDetailsDto);
		return new Response(result, SUCCESS_MESSAGE, true);
	}
}
