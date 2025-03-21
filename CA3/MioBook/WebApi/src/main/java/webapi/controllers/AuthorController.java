package webapi.controllers;

import application.result.Result;
import application.usecase.UseCaseType;
import application.usecase.admin.author.AddAuthor;
import application.usecase.user.author.GetAuthor;
import domain.entities.Author;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import webapi.response.Response;
import webapi.services.AuthenticationService;
import webapi.services.UseCaseService;
import webapi.views.author.AuthorView;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authors")
public class AuthorController {

	private static final String ADD_AUTHOR_MESSAGE =  "Author added successfully.";

	private final UseCaseService useCaseService;
	private final AuthenticationService authenticationService;

	@PostMapping
	public Response<?> addAuthor(@Valid @RequestBody AddAuthor.AddAuthorData data) {
		authenticationService.validateSomeOneLoggedIn();

		AddAuthor useCase = (AddAuthor) useCaseService.getUseCase(UseCaseType.ADD_AUTHOR);
		Result<Author> result = useCase.perform(data, authenticationService.getUserRole());
		if (result.isFailure())
			throw result.exception();

		return Response.of(CREATED, ADD_AUTHOR_MESSAGE);
	}

	@GetMapping("/{name}")
	public Response<AuthorView> getAuthor(@PathVariable String name) {
		GetAuthor useCase = (GetAuthor) useCaseService.getUseCase(UseCaseType.GET_AUTHOR);
		Result<Author> result = useCase.perform(name);
		if (result.isFailure())
			throw result.exception();

		return Response.of(new AuthorView(result.data()), OK);
	}
}
