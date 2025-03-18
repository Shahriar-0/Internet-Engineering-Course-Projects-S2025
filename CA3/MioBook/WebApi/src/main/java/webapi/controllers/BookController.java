package webapi.controllers;

import application.page.Page;
import application.result.Result;
import application.usecase.UseCaseType;
import application.usecase.admin.AddBookUseCase;
import application.usecase.customer.AddReviewUseCase;
import application.usecase.customer.GetBookContentUseCase;
import application.usecase.user.GetBookReviewsUseCase;
import application.usecase.user.GetBookUseCase;
import domain.entities.Book;
import domain.valueobjects.BookContent;
import domain.valueobjects.Review;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webapi.services.AuthenticationService;
import webapi.services.UseCaseService;
import webapi.views.book.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/book") // TODO: maybe change to books?
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
	public ResponseEntity<BookView> getBook(@NotBlank @PathVariable String title) {
		GetBookUseCase useCase = (GetBookUseCase) useCaseService.getUseCase(UseCaseType.GET_BOOK);
		Result<Book> result = useCase.perform(title);
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok(new BookView(result.getData()));
	}

	@GetMapping("/{title}/content")
	public ResponseEntity<BookContentView> getBookContent(@NotBlank @PathVariable String title) {
		GetBookContentUseCase useCase = (GetBookContentUseCase) useCaseService.getUseCase(UseCaseType.GET_BOOK);
		Result<BookContent> result = useCase.perform(
			new GetBookContentUseCase.GetBookContentData(title),
			authenticationService.getUserName(),
			authenticationService.getUserRole()
		); // TODO: refactor this authentication data into another class

		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok(new BookContentView(result.getData()));
	}

	@GetMapping("/{title}/reviews")
	public ResponseEntity<Page<BookReviewsView>> getBookReviews(
		@NotBlank @PathVariable String title,
		@Positive @RequestParam Integer pageNumber,
		@Positive @RequestParam Integer pageSize
	) {
		GetBookReviewsUseCase.ReviewFilter filter = new GetBookReviewsUseCase.ReviewFilter(title, pageNumber, pageSize);
		GetBookReviewsUseCase useCase = (GetBookReviewsUseCase) useCaseService.getUseCase(UseCaseType.GET_BOOK_REVIEWS);
		Result<Page<Review>> result = useCase.perform(filter);
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok(BookReviewsView.mapToView(result.getData()));
	}

	@GetMapping
	public ResponseEntity<Page<BookView>> searchBook(@Valid @ModelAttribute GetBookUseCase.BookFilter filter) {
		GetBookUseCase useCase = (GetBookUseCase) useCaseService.getUseCase(UseCaseType.GET_BOOK);
		Result<Page<Book>> result = useCase.perform(filter);
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok(BookView.mapToView(result.getData()));
	}

	@PostMapping("/review")
	public ResponseEntity<String> addReview(@Valid @RequestBody AddReviewUseCase.AddReviewData data) {
		authenticationService.validateSomeOneLoggedIn();

		AddReviewUseCase useCase = (AddReviewUseCase) useCaseService.getUseCase(UseCaseType.ADD_REVIEW);
		Result<Book> result = useCase.perform(
			data,
			authenticationService.getUserName(),
			authenticationService.getUserRole()
		);

		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok("Review added successfully.");
	}
}
