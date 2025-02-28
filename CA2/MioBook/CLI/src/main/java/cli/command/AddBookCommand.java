package cli.command;

import application.result.Result;
import application.services.AdminService;
import cli.dtos.AddBookDto;
import cli.response.Response;
import domain.entities.Book;
import domain.valueobjects.BookContent;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddBookCommand implements IBaseCommand {

    private static final String SUCCESS_MESSAGE = "Book added successfully.";

    private final AddBookDto addBookDto;
    private final AdminService adminService;

    @Override
    public Response execute() {
        Result<Book> result = adminService.addBook(createBook(addBookDto), addBookDto.username());
        return new Response(result, SUCCESS_MESSAGE, false);
    }

    private Book createBook(AddBookDto dto) {
        return Book.builder()
            .key(dto.title())
            .authorString(dto.author()) // FIXME: this is not a good thing, it takes memory and it's bullshit
            .publisher(dto.publisher())
            .year(dto.year())
            .price(dto.price())
            .synopsis(dto.synopsis())
            .content(new BookContent(dto.content()))
            .genres(dto.genres())
            .build();
    }
}
