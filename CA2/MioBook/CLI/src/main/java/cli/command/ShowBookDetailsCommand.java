package cli.command;

import application.dtos.ShowBookDetailsDto;
import application.result.Result;
import application.services.UserService;
import cli.response.Response;
import domain.entities.Book;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShowBookDetailsCommand implements IBaseCommand {

    private static final String SUCCESS_MESSAGE = "Book details retrieved successfully.";
    private final ShowBookDetailsDto showBookDetailsDto;
    private final UserService userService;

    @Override
    public Response execute() {
        Result<Book> result = userService.showBookDetails(showBookDetailsDto);
        return new Response(result, SUCCESS_MESSAGE, true);
    }
}
