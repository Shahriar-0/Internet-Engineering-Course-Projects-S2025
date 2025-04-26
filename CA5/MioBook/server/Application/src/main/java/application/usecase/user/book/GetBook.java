package application.usecase.user.book;

import application.pagination.Page;
import application.repositories.IBookRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.book.Book;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetBook implements IUseCase {

	private static final int MAX_BOOK_PAGE_SIZE = 100;
	private static final int DEFAULT_BOOK_PAGE_SIZE = 20;
	private static final int DEFAULT_BOOK_PAGE_NUMBER = 1;

	private final IBookRepository bookRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.GET_BOOK;
	}

	public Result<Book> perform(String title) {
		assert title != null && !title.isBlank() : "we rely on presentation layer validation for field 'title'";

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
			filter.sortBy,
			BookFilter.fromString(filter.sortBy),
			(filter.isAscending != null) ? filter.isAscending : false,
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
		String sortBy,
		BookSortByType sortByType,
		Boolean isAscending,
		@Positive Integer pageNumber,
		@Positive Integer pageSize
	) {
		public enum BookSortByType {
			DATE,
			RATING,
			REVIEWS,
			TITLE
		}

		public static BookSortByType fromString(String sortBy) {
			try {
				return BookSortByType.valueOf(sortBy.toUpperCase());
			}
			catch (IllegalArgumentException | NullPointerException e) {
				return BookSortByType.TITLE;  // default sort by title
			}
		}
	}
}
