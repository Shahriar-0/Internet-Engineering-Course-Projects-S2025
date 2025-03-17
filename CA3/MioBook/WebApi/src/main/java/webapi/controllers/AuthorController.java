package webapi.controllers;

import application.result.Result;
import application.uscase.UseCaseType;
import application.uscase.admin.AddAuthorUseCase;
import application.uscase.user.GetAuthorUseCase;
import domain.entities.Author;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webapi.services.AuthenticationService;
import webapi.services.UseCaseService;
import webapi.views.author.AuthorView;

@RequiredArgsConstructor
@RestController
@RequestMapping("/author")
public class AuthorController {

	private final UseCaseService useCaseService;
	private final AuthenticationService authenticationService;

	@PostMapping
	public ResponseEntity<String> addAuthor(@Valid @RequestBody AddAuthorUseCase.AddAuthorData data) {
		authenticationService.validateSomeOneLoggedIn();

		AddAuthorUseCase useCase = (AddAuthorUseCase) useCaseService.getUseCase(UseCaseType.ADD_AUTHOR);
		Result<Author> result = useCase.perform(data, authenticationService.getUserRole());
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok("Author added successfully.");
	}

	@GetMapping("/{name}")
	public ResponseEntity<AuthorView> getAuthor(@NotBlank @RequestParam String name) {
		GetAuthorUseCase useCase = (GetAuthorUseCase) useCaseService.getUseCase(UseCaseType.GET_AUTHOR);
		Result<Author> result = useCase.perform(name);
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok(new AuthorView(result.getData()));
	}
}
