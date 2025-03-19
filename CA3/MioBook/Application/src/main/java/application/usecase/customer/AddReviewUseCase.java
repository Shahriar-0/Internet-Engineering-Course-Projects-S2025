package application.usecase.customer;

import java.time.LocalDateTime;

import application.exceptions.businessexceptions.userexceptions.BookIsNotAccessible;
import application.exceptions.businessexceptions.userexceptions.InvalidAccess;
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
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@RequiredArgsConstructor
public class AddReviewUseCase implements IUseCase {

	private final IUserRepository userRepository;
	private final IBookRepository bookRepository;

    private boolean enforceAccessChecks = true; // for data initialization it would be false otherwise it would be true

	@Override
	public UseCaseType getType() {
		return UseCaseType.ADD_REVIEW;
	}

	public Result<Book> perform(AddReviewData data, String userName, User.Role role) {
        if (!User.Role.CUSTOMER.equals(role))
            return Result.failure(new InvalidAccess("customer"));

        Result<User> userResult = userRepository.get(userName);
        if (userResult.isFailure())
            return Result.failure(userResult.getException());
        assert userResult.getData() instanceof Customer: "we relay on role passing from presentation layer";
        Customer customer = (Customer) userResult.getData();

        Result<Book> bookResult = bookRepository.get(data.title);
        if (bookResult.isFailure())
            return Result.failure(bookResult.getException());
        Book book = bookResult.getData();

        if (enforceAccessChecks && !customer.hasBought(book))
            return Result.failure(new BookIsNotAccessible(data.title));

        book.addReview(mapToReview(data, customer));
        return Result.success(book);
    }

    private Review mapToReview(AddReviewData data, Customer customer) {
        return new Review(data.rating, data.comment, customer, LocalDateTime.now());
    }

	public record AddReviewData(
        @NotBlank
        String title,

        @NotNull
        @Min(value = 1)
        @Max(value = 5)
        Integer rating,

        @NotBlank
        String comment
	) {}
}
