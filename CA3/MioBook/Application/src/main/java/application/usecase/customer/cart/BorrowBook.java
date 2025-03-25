package application.usecase.customer.cart;

import application.exceptions.businessexceptions.bookexceptions.BookDoesntExist;
import application.exceptions.businessexceptions.cartexceptions.CantAddToCart;
import application.repositories.IBookRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.book.Book;
import domain.entities.user.Customer;
import domain.entities.user.User;
import domain.exceptions.DomainException;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BorrowBook implements IUseCase {
	private final IBookRepository bookRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.BORROW_BOOK;
	}

	public Result<Customer> perform(BorrowBookData data, User user) {
		assert user instanceof Customer: "we rely on presentation layer access control";
		Customer customer = (Customer) user;

		Result<Book> bookResult = bookRepository.get(data.title);
		if (bookResult.isFailure())
			return Result.failure(new BookDoesntExist(data.title));
		Book book = bookResult.data();

        List<DomainException> exceptions = customer.getCart().getAddBookErrors(data.title);
        if (!exceptions.isEmpty())
            return Result.failure(new CantAddToCart(exceptions.getFirst().getMessage()));

		customer.getCart().borrowBook(book, data.borrowedDays);
		return Result.success(customer);
	}

	public record BorrowBookData(
		@NotBlank String title,

		@NotNull
		@Min(value = 1)
		@Max(value = 9)
		Integer borrowedDays
	) {}
}
