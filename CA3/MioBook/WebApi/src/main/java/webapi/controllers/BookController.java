package webapi.controllers;

import application.page.Page;
import application.result.Result;
import application.uscase.UseCaseType;
import application.uscase.admin.AddBookUseCase;
import application.uscase.user.GetBookUseCase;
import domain.entities.Book;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webapi.services.AuthenticationService;
import webapi.services.UseCaseService;
import webapi.views.book.BookView;

@RequiredArgsConstructor
@RestController
@RequestMapping("/book")
public class BookController {

	private final UseCaseService useCaseService;
	private final AuthenticationService authenticationService;

	@PostMapping
	public ResponseEntity<String> addBook(@Valid @RequestBody AddBookUseCase.AddBookData data) {
		authenticationService.validateSomeOneLoggedIn();

		AddBookUseCase useCase = (AddBookUseCase) useCaseService.getUseCase(UseCaseType.ADD_BOOK);
		Result<Book> result = useCase.perform(data, authenticationService.getUserRole());
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok("Book added successfully.");
	}

	@GetMapping("/{title}")
	public ResponseEntity<BookView> getBook(@NotBlank @RequestParam String title) {
		GetBookUseCase useCase = (GetBookUseCase) useCaseService.getUseCase(UseCaseType.GET_BOOK);
		Result<Book> result = useCase.perform(title);
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok(new BookView(result.getData()));
	}

	@GetMapping
	public ResponseEntity<Page<BookView>> searchBook(@Valid @ModelAttribute GetBookUseCase.BookFilter filter) {
		GetBookUseCase useCase = (GetBookUseCase) useCaseService.getUseCase(UseCaseType.GET_BOOK);
		Result<Page<Book>> result = useCase.perform(filter);
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok(BookView.mapToView(result.getData()));
	}
}
