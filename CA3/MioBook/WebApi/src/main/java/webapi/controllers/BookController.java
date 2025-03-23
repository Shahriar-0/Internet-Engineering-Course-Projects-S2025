package webapi.controllers;

import application.pagination.Page;
import application.result.Result;
import application.usecase.UseCaseType;
import application.usecase.admin.book.AddBook;
import application.usecase.customer.book.AddReview;
import application.usecase.customer.book.GetBookContent;
import application.usecase.user.book.GetBook;
import application.usecase.user.book.GetBookReviews;
import domain.entities.Book;
import domain.valueobjects.BookContent;
import domain.valueobjects.Review;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import webapi.accesscontrol.Access;
import webapi.response.Response;
import webapi.services.AuthenticationService;
import webapi.services.UseCaseService;
import webapi.views.book.BookContentView;
import webapi.views.book.BookReviewsView;
import webapi.views.book.BookView;

import static domain.entities.User.Role.ADMIN;
import static domain.entities.User.Role.CUSTOMER;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

	private final UseCaseService useCaseService;
	private final AuthenticationService authenticationService;

	@PostMapping
	@Access(roles = {ADMIN})
	public Response<?> addBook(@Valid @RequestBody AddBook.AddBookData data) {
		AddBook useCase = (AddBook) useCaseService.getUseCase(UseCaseType.ADD_BOOK);

		Result<Book> result = useCase.perform(data, authenticationService.getUser());
		if (result.isFailure())
			throw result.exception();

		return Response.of(CREATED, "Book added successfully.");
	}

	@GetMapping("/{title}")
	@Access(isWhiteList = false)
	public Response<BookView> getBook(@NotBlank @PathVariable String title) {
		GetBook useCase = (GetBook) useCaseService.getUseCase(UseCaseType.GET_BOOK);

		Result<Book> result = useCase.perform(title);
		if (result.isFailure())
			throw result.exception();

		return Response.of(new BookView(result.data()), OK);
	}

	@GetMapping("/{title}/content")
	@Access(roles = {CUSTOMER})
	public Response<BookContentView> getBookContent(@NotBlank @PathVariable String title) {
		GetBookContent useCase = (GetBookContent) useCaseService.getUseCase(UseCaseType.GET_BOOK_CONTENT);

		Result<BookContent> result = useCase.perform(title, authenticationService.getUser());
		if (result.isFailure())
			throw result.exception();

		return Response.of(new BookContentView(result.data()), OK);
	}

	@GetMapping("/{title}/reviews")
	@Access(isWhiteList = false)
	public Response<Page<BookReviewsView>> getBookReviews(
		@PathVariable String title,
		@Valid @ModelAttribute GetBookReviews.ReviewFilter filter
	) {
		GetBookReviews useCase = (GetBookReviews) useCaseService.getUseCase(UseCaseType.GET_BOOK_REVIEWS);

		Result<Page<Review>> result = useCase.perform(title, filter);
		if (result.isFailure())
			throw result.exception();

		return Response.of(BookReviewsView.mapToView(result.data()), OK);
	}

	@GetMapping
	@Access(isWhiteList = false)
	public Response<Page<BookView>> searchBook(@Valid @ModelAttribute GetBook.BookFilter filter) {
		GetBook useCase = (GetBook) useCaseService.getUseCase(UseCaseType.GET_BOOK);

		Result<Page<Book>> result = useCase.perform(filter);
		if (result.isFailure())
			throw result.exception();

		return Response.of(BookView.mapToView(result.data()), OK);
	}

	@PostMapping("/{title}/reviews")
	@Access(roles = {CUSTOMER})
	public Response<?> addReview(@Valid @RequestBody AddReview.AddReviewData data, @PathVariable String title) {
		AddReview useCase = (AddReview) useCaseService.getUseCase(UseCaseType.ADD_REVIEW);

		Result<Book> result = useCase.perform(data, title, authenticationService.getUser());
		if (result.isFailure())
			throw result.exception();

		return Response.of(CREATED, "Review added successfully.");
	}
}
