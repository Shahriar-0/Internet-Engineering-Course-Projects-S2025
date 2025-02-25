package cli.command;

import application.result.Result;
import application.services.AdminService;
import cli.dtos.AddAuthorDto;
import cli.response.Response;
import domain.entities.Author;
import java.time.LocalDate;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddAuthorCommand implements IBaseCommand {

	private static final String SUCCESS_MESSAGE = "Author added successfully.";

	private final AddAuthorDto addAuthorDto;
	private final AdminService adminService;

	@Override
	public Response execute() {
		Result<Author> result = adminService.addAuthor(createAuthor(addAuthorDto), addAuthorDto.username());
		return createResponse(result);
	}

	private Response createResponse(Result<Author> result) {
		String message = result.isSuccessful() ? SUCCESS_MESSAGE : result.getException().getMessage();
		return new Response(result.isSuccessful(), message, null);
	}

	private Author createAuthor(AddAuthorDto dto) {
		return Author
			.builder()
			.key(dto.name())
			.penName(dto.penName())
			.nationality(dto.nationality())
			.born(LocalDate.parse(dto.born()))
			.died(dto.died() == null ? null : LocalDate.parse(dto.died()))
			.build();
	}
}
