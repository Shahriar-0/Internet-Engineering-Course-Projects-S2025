package application.usecase.customer.book;

import application.exceptions.businessexceptions.userexceptions.BookIsNotAccessible;
import application.repositories.IBookRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.book.Book;
import domain.entities.book.Review;
import domain.entities.user.Customer;
import domain.entities.user.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@RequiredArgsConstructor
public class AddReview implements IUseCase {

	private final IBookRepository bookRepository;

    private boolean enforceAccessChecks = true; // for data initialization it would be false otherwise it would be true

	@Override
	public UseCaseType getType() {
		return UseCaseType.ADD_REVIEW;
	}

	public Result<Book> perform(AddReviewData data, String title, User user) {
        assert user instanceof Customer: "we rely on presentation layer access control";
        Customer customer = (Customer) user;

        Result<Book> bookResult = bookRepository.findByTitle(title);
        if (bookResult.isFailure())
            return Result.failure(bookResult.exception());
        Book book = bookResult.data();

        if (enforceAccessChecks && !customer.hasAccess(title))
            return Result.failure(new BookIsNotAccessible(title));

        Review review = new Review(data.rating, data.comment, customer, book);
        book.addReview(review);
        return Result.success(book);
    }

	public record AddReviewData(
        @NotNull
        @Min(value = 1)
        @Max(value = 5)
        Integer rating,

        @NotBlank
        String comment
	) {}
}
