package webapi.controllers;

import application.result.Result;
import application.usecase.UseCaseType;
import application.usecase.admin.author.AddAuthor;
import application.usecase.user.author.GetAuthor;
import domain.entities.author.Author;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import webapi.accesscontrol.Access;
import webapi.response.Response;
import webapi.services.AuthenticationService;
import webapi.services.UseCaseService;
import webapi.views.author.AuthorView;
import webapi.views.page.PageView;
import java.util.List;

import static domain.entities.user.Role.ADMIN;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


@RequiredArgsConstructor
@RestController
@RequestMapping("/authors")
public class AuthorController {

	private static final String ADD_AUTHOR_MESSAGE =  "Author added successfully.";

	private final UseCaseService useCaseService;
	private final AuthenticationService authenticationService;

	@PostMapping
	@Access(roles = {ADMIN})
	public Response<?> addAuthor(@Valid @RequestBody AddAuthor.AddAuthorData data) {
		AddAuthor useCase = (AddAuthor) useCaseService.getUseCase(UseCaseType.ADD_AUTHOR);

		Result<Author> result = useCase.perform(data, authenticationService.getUser());
		if (result.isFailure())
			throw result.exception();

		return Response.of(CREATED, ADD_AUTHOR_MESSAGE);
	}

	@GetMapping("/{name}")
	@Access
	public Response<AuthorView> getAuthor(@PathVariable String name) {
		GetAuthor useCase = (GetAuthor) useCaseService.getUseCase(UseCaseType.GET_AUTHOR);

		Result<Author> result = useCase.perform(name);
		if (result.isFailure())
			throw result.exception();

		return Response.of(new AuthorView(result.data()), OK);
	}

	@GetMapping
	@Access
	public Response<List<AuthorView>> getAuthors() {
		GetAuthor useCase = (GetAuthor) useCaseService.getUseCase(UseCaseType.GET_AUTHOR);

		List<Author> authors = useCase.perform();
		return Response.of(AuthorView.mapToView(authors), OK);
	}

	@GetMapping("/search")
	@Access
	public Response<PageView<AuthorView>> searchAuthors(@Valid @ModelAttribute GetAuthor.AuthorFilter filter) {
		GetAuthor useCase = (GetAuthor) useCaseService.getUseCase(UseCaseType.GET_AUTHOR);

		Page<Author> authors = useCase.perform(filter);
		return Response.of(
			new PageView<>(AuthorView.mapToView(authors)),
			OK
		);
	}
}
