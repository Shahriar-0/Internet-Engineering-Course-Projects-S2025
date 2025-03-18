package application.usecase.user;

import application.page.Page;
import application.repositories.IBookRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.Book;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetBookUseCase implements IUseCase {

	private static final int MAX_BOOK_PAGE_SIZE = 100;
	private static final int DEFAULT_BOOK_PAGE_SIZE = 20;
	private static final int DEFAULT_BOOK_PAGE_NUMBER = 1;

	private final IBookRepository bookRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.GET_BOOK;
	}

	public Result<Book> perform(String title) {
		assert title != null && !title.isBlank() : "we relay on @NotBlank validation on title field in presentation layer";

		return bookRepository.find(title);
	}

	public Result<Page<Book>> perform(BookFilter filter) {
		BookFilter standardFilter = standardizeFilter(filter);
		return Result.success(bookRepository.filter(standardFilter));
	}

	private static BookFilter standardizeFilter(BookFilter filter) {
		return new BookFilter(
			filter.title,
			filter.author,
			filter.genre,
			filter.from,
			filter.to,
			(filter.ascendingSortByRating != null) ? filter.ascendingSortByRating : true,
			(filter.pageNumber != null) ? filter.pageNumber : DEFAULT_BOOK_PAGE_NUMBER,
			standardizePageSizeField(filter.pageSize)
		);
	}

	private static int standardizePageSizeField(Integer currentPageSize) {
		if (currentPageSize == null)
			return DEFAULT_BOOK_PAGE_SIZE;

		if (currentPageSize > MAX_BOOK_PAGE_SIZE)
			return MAX_BOOK_PAGE_SIZE;

		return currentPageSize;
	}

	public record BookFilter(
		String title,
		String author,
		String genre,
		@Positive Integer from,
		@Positive Integer to,
		Boolean ascendingSortByRating,
		@Positive Integer pageNumber,
		@Positive Integer pageSize
	) {}
}
