package cli.command;

import application.dtos.AddAuthorDto;
import application.result.Result;
import application.services.AdminService;
import cli.response.Response;
import domain.entities.Author;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddAuthorCommand implements IBaseCommand {

	private static final String SUCCESS_MESSAGE = "Author added successfully.";

	private final AddAuthorDto addAuthorDto;
	private final AdminService adminService;

	@Override
	public Response execute() {
		Result<Author> result = adminService.addAuthor(addAuthorDto);
		return new Response(result, SUCCESS_MESSAGE, false);
	}
}
