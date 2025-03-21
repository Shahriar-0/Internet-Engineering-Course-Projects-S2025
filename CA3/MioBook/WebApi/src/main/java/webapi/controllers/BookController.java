package webapi.controllers;

import application.pagination.Page;
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
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import webapi.response.Response;
import webapi.services.AuthenticationService;
import webapi.services.UseCaseService;
import webapi.views.book.BookContentView;
import webapi.views.book.BookReviewsView;
import webapi.views.book.BookView;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

	private final UseCaseService useCaseService;
	private final AuthenticationService authenticationService;

	@PostMapping
	public Response<?> addBook(@Valid @RequestBody AddBookUseCase.AddBookData data) {
		authenticationService.validateSomeOneLoggedIn();

		AddBookUseCase useCase = (AddBookUseCase) useCaseService.getUseCase(UseCaseType.ADD_BOOK);
		Result<Book> result = useCase.perform(data, authenticationService.getUserRole());
		if (result.isFailure())
			throw result.getException();

		return Response.of(CREATED, "Book added successfully.");
	}

	@GetMapping("/{title}")
	public Response<BookView> getBook(@NotBlank @PathVariable String title) {
		GetBookUseCase useCase = (GetBookUseCase) useCaseService.getUseCase(UseCaseType.GET_BOOK);
		Result<Book> result = useCase.perform(title);
		if (result.isFailure())
			throw result.getException();

		return Response.of(new BookView(result.getData()), OK);
	}

	@GetMapping("/{title}/content")
	public Response<BookContentView> getBookContent(@NotBlank @PathVariable String title) {
		authenticationService.validateSomeOneLoggedIn();

		GetBookContentUseCase useCase = (GetBookContentUseCase) useCaseService.getUseCase(UseCaseType.GET_BOOK_CONTENT);
		Result<BookContent> result = useCase.perform(title, authenticationService.getUserName(), authenticationService.getUserRole());

		if (result.isFailure())
			throw result.getException();

		return Response.of(new BookContentView(result.getData()), OK);
	}

	@GetMapping("/{title}/reviews")
	public Response<Page<BookReviewsView>> getBookReviews(
		@NotBlank @PathVariable String title,
		@Valid @ModelAttribute GetBookReviewsUseCase.ReviewFilter filter
	) {
		GetBookReviewsUseCase useCase = (GetBookReviewsUseCase) useCaseService.getUseCase(UseCaseType.GET_BOOK_REVIEWS);
		Result<Page<Review>> result = useCase.perform(title, filter);
		if (result.isFailure())
			throw result.getException();

		return Response.of(BookReviewsView.mapToView(result.getData()), OK);
	}

	@GetMapping
	public Response<Page<BookView>> searchBook(@Valid @ModelAttribute GetBookUseCase.BookFilter filter) {
		GetBookUseCase useCase = (GetBookUseCase) useCaseService.getUseCase(UseCaseType.GET_BOOK);
		Result<Page<Book>> result = useCase.perform(filter);
		if (result.isFailure())
			throw result.getException();

		return Response.of(BookView.mapToView(result.getData()), OK);
	}

	@PostMapping("/{title}/reviews")
	public Response<?> addReview(@Valid @RequestBody AddReviewUseCase.AddReviewData data, @PathVariable String title) {
		authenticationService.validateSomeOneLoggedIn();

		AddReviewUseCase useCase = (AddReviewUseCase) useCaseService.getUseCase(UseCaseType.ADD_REVIEW);
		Result<Book> result = useCase.perform(
			data,
			title,
			authenticationService.getUserName(),
			authenticationService.getUserRole()
		);

		if (result.isFailure())
			throw result.getException();

		return Response.of(CREATED, "Review added successfully.");
	}
}
