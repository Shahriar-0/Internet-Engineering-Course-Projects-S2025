package cli.command;

import application.dtos.SearchBooksDto;
import application.result.Result;
import application.services.UserService;
import cli.response.Response;
import domain.valueobjects.BookSearch;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SearchBooksCommand implements IBaseCommand {

    private final SearchBooksDto searchBooksDto;
    private final UserService userService;

    @Override
    public Response execute() {
        Result<BookSearch> result = userService.searchBooks(searchBooksDto);
        return new Response(result, searchBooksDto.getSuccessMessage(), true);
    }
}
