package cli.command;

import application.dtos.AddBookDto;
import application.result.Result;
import application.services.AdminService;
import cli.response.Response;
import domain.entities.Book;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddBookCommand implements IBaseCommand {

    private static final String SUCCESS_MESSAGE = "Book added successfully.";

    private final AddBookDto addBookDto;
    private final AdminService adminService;

    @Override
    public Response execute() {
        Result<Book> result = adminService.addBook(addBookDto);
        return new Response(result, SUCCESS_MESSAGE, false);
    }
}
