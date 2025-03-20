package application.usecase.customer;

import application.exceptions.businessexceptions.bookexceptions.BookDoesntExist;
import application.exceptions.businessexceptions.cartexceptions.CantAddToCart;
import application.exceptions.businessexceptions.userexceptions.InvalidAccess;
import application.repositories.IBookRepository;
import application.repositories.IUserRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.Book;
import domain.entities.Customer;
import domain.entities.User;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddCartUseCase implements IUseCase {
	private final IUserRepository userRepository;
	private final IBookRepository bookRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.ADD_CART;
	}

	public Result<Customer> perform(AddCartData data, String username, User.Role role) {
		if (!User.Role.CUSTOMER.equals(role))
			return Result.failure(new InvalidAccess("customer"));

		Result<User> userResult = userRepository.get(username);
		if (userResult.isFailure())
			return Result.failure(userResult.getException());
		assert userResult.getData() instanceof Customer : "we relay on role passing from presentation layer";
		Customer customer = (Customer) userResult.getData();

		Result<Book> bookResult = bookRepository.get(data.title);
		if (bookResult.isFailure())
			return Result.failure(new BookDoesntExist(data.title));
		Book book = bookResult.getData();

		if (!customer.canAddBook(book))
			return Result.failure(new CantAddToCart(customer.findAddBookErrors(book)));

		customer.addBook(book);
		return Result.success(customer);
	}

	public record AddCartData(
    	@NotBlank String title
  	) {}
}
