package cli.command;

import application.dtos.BorrowBookDto;
import application.result.Result;
import application.services.UserService;
import cli.response.Response;
import domain.entities.User;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class BorrowBookCommand implements IBaseCommand {

	private static final String SUCCESS_MESSAGE = "Added borrowed book to cart.";

	private final BorrowBookDto borrowBookDto;
	private final UserService userService;

	@Override
	public Response execute() {
		Result<User> userSearchResult = userService.borrowBook(borrowBookDto);
		return new Response(userSearchResult, SUCCESS_MESSAGE, false);
	}
}
