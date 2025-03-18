package webapi.controllers;

import application.result.Result;
import application.usecase.UseCaseType;
import application.usecase.admin.AddAuthorUseCase;
import application.usecase.user.GetAuthorUseCase;
import domain.entities.Author;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import webapi.services.UseCaseService;
import webapi.views.author.AuthorView;

@RequiredArgsConstructor
@RestController
@RequestMapping("/authors")
public class AuthorController {

	private final UseCaseService useCaseService;

	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> addAuthor(@Valid @RequestBody AddAuthorUseCase.AddAuthorData data) {
		AddAuthorUseCase useCase = (AddAuthorUseCase) useCaseService.getUseCase(UseCaseType.ADD_AUTHOR);
		Result<Author> result = useCase.perform(data);
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok("Author added successfully.");
	}

	@GetMapping("/{name}")
	public ResponseEntity<AuthorView> getAuthor(@NotBlank @PathVariable String name) {
		GetAuthorUseCase useCase = (GetAuthorUseCase) useCaseService.getUseCase(UseCaseType.GET_AUTHOR);
		Result<Author> result = useCase.perform(name);
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok(new AuthorView(result.getData()));
	}
}
