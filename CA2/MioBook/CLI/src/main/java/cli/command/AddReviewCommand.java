package cli.command;

import application.dtos.AddReviewDto;
import application.result.Result;
import application.services.UserService;
import cli.response.Response;
import domain.entities.Book;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddReviewCommand implements IBaseCommand {

    private static final String SUCCESS_MESSAGE = "Review added successfully.";

    private final AddReviewDto addReviewDto;
    private final UserService userService;

    @Override
    public Response execute() {
        Result<Book> result = userService.addReview(addReviewDto);
        return new Response(result, SUCCESS_MESSAGE, false);
    }
}
