package webapi.controllers;

import application.result.Result;
import application.usecase.UseCaseType;
import application.usecase.admin.AddAuthorUseCase;
import application.usecase.user.GetAuthorUseCase;
import domain.entities.Author;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
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
	public Response<?> addAuthor(@Valid @RequestBody AddAuthorUseCase.AddAuthorData data) {
		authenticationService.validateSomeOneLoggedIn();

		AddAuthorUseCase useCase = (AddAuthorUseCase) useCaseService.getUseCase(UseCaseType.ADD_AUTHOR);
		Result<Author> result = useCase.perform(data, authenticationService.getUserRole());
		if (result.isFailure())
			throw result.getException();

		return Response.of(CREATED, ADD_AUTHOR_MESSAGE);
	}

	@GetMapping("/{name}")
	public Response<AuthorView> getAuthor(@NotBlank @PathVariable String name) {
		GetAuthorUseCase useCase = (GetAuthorUseCase) useCaseService.getUseCase(UseCaseType.GET_AUTHOR);
		Result<Author> result = useCase.perform(name);
		if (result.isFailure())
			throw result.getException();

		return Response.of(new AuthorView(result.getData()), OK);
	}
}
