package application.usecase.user.book;

import application.pagination.Page;
import application.repositories.IBookRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.Book;
import domain.valueobjects.Review;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetBookReviews implements IUseCase {

    private static final int MAX_REVIEW_PAGE_SIZE = 20;
	private static final int DEFAULT_REVIEW_PAGE_SIZE = 5;
	private static final int DEFAULT_REVIEW_PAGE_NUMBER = 1;

	private final IBookRepository bookRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.GET_BOOK_REVIEWS;
	}

	public Result<Page<Review>> perform(String title, ReviewFilter filter) {
        ReviewFilter standardizeFilter = standardizeFilter(filter);
        Result<Book> bookResult = bookRepository.get(title);
        if (bookResult.isFailure())
            return Result.failure(bookResult.exception());

        return Result.success(bookRepository.filter(bookResult.data(), standardizeFilter));
	}

    private static ReviewFilter standardizeFilter(ReviewFilter filter) {
        return new ReviewFilter(
            filter.pageNumber() == null ? DEFAULT_REVIEW_PAGE_NUMBER : filter.pageNumber(),
            standardizePageSizeField(filter.pageSize())
        );
    }

    private static int standardizePageSizeField(Integer currentPageSize) {
		if (currentPageSize == null)
			return DEFAULT_REVIEW_PAGE_SIZE;

		if (currentPageSize > MAX_REVIEW_PAGE_SIZE)
			return MAX_REVIEW_PAGE_SIZE;

		return currentPageSize;
	}

	public record ReviewFilter(
        @Positive Integer pageNumber,
        @Positive Integer pageSize
    ) {}
}
