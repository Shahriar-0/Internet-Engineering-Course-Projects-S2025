package application.uscase.customer;

import application.exceptions.businessexceptions.cartexceptions.CantAddToCart;
import application.exceptions.businessexceptions.userexceptions.InvalidAccess;
import application.repositories.IBookRepository;
import application.repositories.IUserRepository;
import application.result.Result;
import application.uscase.IUseCase;
import application.uscase.UseCaseType;
import domain.entities.Book;
import domain.entities.Customer;
import domain.entities.User;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BorrowBookUseCase implements IUseCase {

	private final IUserRepository userRepository;
	private final IBookRepository bookRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.BORROW_BOOK;
	}

	public Result<Customer> perform(BorrowBookData data, String username, User.Role role) {
		if (!User.Role.CUSTOMER.equals(role))
			return Result.failure(new InvalidAccess("customer"));

		Result<User> userResult = userRepository.get(username);
		if (userResult.isFailure())
			return Result.failure(userResult.getException());
		assert userResult.getData() instanceof Customer : "we relay on role passing from presentation layer";
		Customer customer = (Customer) userResult.getData();

		Result<Book> bookResult = bookRepository.get(data.title);
		if (bookResult.isFailure())
			return Result.failure(bookResult.getException());
		Book book = bookResult.getData();

		if (!customer.canAddBook(book))
			return Result.failure(new CantAddToCart(customer.findAddBookErrors(book))); // for now their validations are the same

		customer.borrowBook(book, data.borrowedDays);
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
