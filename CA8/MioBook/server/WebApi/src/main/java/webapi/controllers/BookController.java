package webapi.controllers;

import application.result.Result;
import application.usecase.UseCaseType;
import application.usecase.admin.book.AddBook;
import application.usecase.customer.book.AddReview;
import application.usecase.customer.book.GetBookContent;
import application.usecase.user.book.GetBook;
import application.usecase.user.book.GetBookReviews;
import application.usecase.user.book.GetGenres;
import domain.entities.book.Book;
import domain.entities.book.BookContent;
import domain.entities.book.Review;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import webapi.accesscontrol.Access;
import webapi.response.Response;
import webapi.services.AuthenticationService;
import webapi.services.UseCaseService;
import webapi.views.book.BookContentView;
import webapi.views.book.BookReviewsView;
import webapi.views.book.BookView;
import webapi.views.page.PageView;

import java.util.List;

import static domain.entities.user.Role.ADMIN;
import static domain.entities.user.Role.CUSTOMER;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

    private static final String ADD_BOOK_MESSAGE = "Book added successfully.";
    private static final String ADD_REVIEW_MESSAGE = "Review added successfully.";

	private final UseCaseService useCaseService;
	private final AuthenticationService authenticationService;

	@PostMapping
	@Access(roles = {ADMIN}, isWhiteList = false)
	public Response<?> addBook(@Valid @RequestBody AddBook.AddBookData data) {
		AddBook useCase = (AddBook) useCaseService.getUseCase(UseCaseType.ADD_BOOK);

		Result<Book> result = useCase.perform(data, authenticationService.getUser());
		if (result.isFailure())
			throw result.exception();

		return Response.of(CREATED, ADD_BOOK_MESSAGE);
	}

	@GetMapping("/{title}")
	@Access(isWhiteList = true)
	public Response<BookView> getBook(@PathVariable String title) {
		GetBook useCase = (GetBook) useCaseService.getUseCase(UseCaseType.GET_BOOK);

		Result<Book> result = useCase.perform(title);
		if (result.isFailure())
			throw result.exception();

		return Response.of(new BookView(result.data()), OK);
	}

	@GetMapping("/{title}/content")
	@Access(roles = {CUSTOMER}, isWhiteList = false)
	public Response<BookContentView> getBookContent(@NotBlank @PathVariable String title) {
		GetBookContent useCase = (GetBookContent) useCaseService.getUseCase(UseCaseType.GET_BOOK_CONTENT);

		Result<BookContent> result = useCase.perform(title, authenticationService.getUser());
		if (result.isFailure())
			throw result.exception();

		return Response.of(new BookContentView(result.data()), OK);
	}

	@GetMapping("/{title}/reviews")
	@Access(isWhiteList = true)
	public Response<PageView<BookReviewsView>> getBookReviews(
		@PathVariable String title,
		@Valid @ModelAttribute GetBookReviews.ReviewFilter filter
	) {
		GetBookReviews useCase = (GetBookReviews) useCaseService.getUseCase(UseCaseType.GET_BOOK_REVIEWS);

		Result<Page<Review>> result = useCase.perform(title, filter);
        if (result.isFailure())
            throw result.exception();

		return Response.of(
            new PageView<>(BookReviewsView.mapToView(result.data())),
            OK
        );
	}

	@GetMapping
	@Access(isWhiteList = true)
	public Response<PageView<BookView>> searchBook(@Valid @ModelAttribute GetBook.BookFilter filter) {
		GetBook useCase = (GetBook) useCaseService.getUseCase(UseCaseType.GET_BOOK);

		Page<Book> books = useCase.perform(filter);
		return Response.of(
            new PageView<>(BookView.mapToView(books)),
            OK
        );
	}

	@PostMapping("/{title}/reviews")
	@Access(roles = {CUSTOMER}, isWhiteList = false)
	public Response<?> addReview(@Valid @RequestBody AddReview.AddReviewData data, @PathVariable String title) {
		AddReview useCase = (AddReview) useCaseService.getUseCase(UseCaseType.ADD_REVIEW);

		Result<Book> result = useCase.perform(data, title, authenticationService.getUser());
		if (result.isFailure())
			throw result.exception();

		return Response.of(CREATED, ADD_REVIEW_MESSAGE);
	}

    @GetMapping("/genres")
    @Access(isWhiteList = true)
    public Response<List<String>> getGenres() {
        GetGenres useCase = (GetGenres) useCaseService.getUseCase(UseCaseType.GET_GENRES);

        List<String> genres = useCase.perform();
        return Response.of(genres, OK);
    }
}
