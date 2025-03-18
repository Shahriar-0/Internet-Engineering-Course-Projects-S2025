package application.usecase.customer;

import application.exceptions.businessexceptions.userexceptions.BookIsNotAccessible;
import application.repositories.IBookRepository;
import application.repositories.IUserRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.Book;
import domain.entities.Customer;
import domain.entities.User;
import domain.valueobjects.Review;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddReviewUseCase implements IUseCase {

	private final IUserRepository userRepository;
	private final IBookRepository bookRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.ADD_REVIEW;
	}

	public Result<Book> perform(AddReviewData data, String userName) {
		Result<User> userResult = userRepository.get(userName);
		if (userResult.isFailure())
            return Result.failure(userResult.getException());
		assert userResult.getData() instanceof Customer : "we relay on role passing from presentation layer";
		Customer customer = (Customer) userResult.getData();

		Result<Book> bookResult = bookRepository.get(data.title);
		if (bookResult.isFailure())
            return Result.failure(bookResult.getException());
		Book book = bookResult.getData();

		if (!customer.hasBought(book))
            return Result.failure(new BookIsNotAccessible(data.title));

		book.addReview(mapToReview(data, customer));
		return Result.success(book);
	}

	private Review mapToReview(AddReviewData data, Customer customer) {
		return new Review(data.rating, data.comment, customer, LocalDateTime.now());
	}

	public record AddReviewData(
		@NotBlank String title,

		@NotNull
        @Min(value = 1)
        @Max(value = 5)
        Integer rating,

		@NotBlank String comment
	) {}
}
