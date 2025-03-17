package application.uscase.user;

import application.page.Page;
import application.repositories.IBookRepository;
import application.result.Result;
import application.uscase.IUseCase;
import application.uscase.UseCaseType;
import domain.entities.Book;
import domain.valueobjects.Review;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetBookReviewsUseCase implements IUseCase {

    private static final int MAX_REVIEW_PAGE_SIZE = 20;
	private static final int DEFAULT_REVIEW_PAGE_SIZE = 5;
	private static final int DEFAULT_REVIEW_PAGE_NUMBER = 1;

	private final IBookRepository bookRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.GET_BOOK_REVIEWS;
	}

	public Result<Page<Review>> perform(ReviewFilter filter) {
        ReviewFilter standardizeFilter = standardizeFilter(filter);
        Result<Book> bookResult = bookRepository.get(standardizeFilter.bookTitle());
        if (bookResult.isFailure())
            return Result.failure(bookResult.getException());

        return Result.success(bookRepository.filter(bookResult.getData(), standardizeFilter));
	}

    private static ReviewFilter standardizeFilter(ReviewFilter filter) {
        return new ReviewFilter(
            filter.bookTitle(),
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
        String bookTitle,
        @Positive Integer pageNumber,
        @Positive Integer pageSize
    ) {}
}
