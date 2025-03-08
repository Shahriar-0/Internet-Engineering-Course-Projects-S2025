package cli.command;

import application.dtos.ShowBookContentDto;
import application.result.Result;
import application.services.UserService;
import cli.response.Response;
import domain.valueobjects.BookContent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShowBookContentCommand implements IBaseCommand {

	private static final String SUCCESS_MESSAGE = "Book content retrieved successfully.";

	private final ShowBookContentDto showBookContentDto;
	private final UserService userService;

	@Override
	public Response execute() {
		Result<BookContent> result = userService.showBookContent(showBookContentDto);
		return new Response(result, SUCCESS_MESSAGE, true);
	}
}
