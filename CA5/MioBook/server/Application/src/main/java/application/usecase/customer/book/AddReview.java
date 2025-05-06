package application.usecase.customer.book;

import application.exceptions.businessexceptions.bookexceptions.BookDoesntExist;
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

import java.util.Optional;

import org.springframework.stereotype.Service;

@Setter
@Service
@RequiredArgsConstructor
public class AddReview implements IUseCase {

	private final IBookRepository bookRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.ADD_REVIEW;
	}

	public Result<Book> perform(AddReviewData data, String title, User user) {
        assert user instanceof Customer: "we rely on presentation layer access control";
        Customer customer = (Customer) user;

        Optional<Book> bookResult = bookRepository.findByTitle(title);
        if (bookResult.isEmpty())
            return Result.failure(new BookDoesntExist(title));
        Book book = bookResult.get();

        if (!customer.hasAccess(title))
            return Result.failure(new BookIsNotAccessible(title));

        Review review = new Review(data.rating, data.comment, customer, book);
        book.addReview(review);
        bookRepository.upsertReview(review, book, customer);
        return Result.success(book);
    }

    public static Review mapToReview(AddReviewData data, Book book, Customer customer) {
        return new Review(data.rating, data.comment, customer, book);
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
