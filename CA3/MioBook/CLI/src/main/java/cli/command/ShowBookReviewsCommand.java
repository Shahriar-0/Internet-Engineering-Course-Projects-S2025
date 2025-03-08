package cli.command;

import application.dtos.ShowBookReviewsDto;
import application.result.Result;
import application.services.UserService;
import cli.response.Response;
import domain.valueobjects.BookReviews;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ShowBookReviewsCommand implements IBaseCommand {

    private static final String SUCCESS_MESSAGE = "Book reviews retrieved successfully.";

    private ShowBookReviewsDto showBookReviewsDto;
    private UserService userService;

    @Override
    public Response execute() {
        Result<BookReviews> result = userService.showBookReviews(showBookReviewsDto);
        return new Response(result, SUCCESS_MESSAGE, true);
    }
}
