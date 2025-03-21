package application.usecase.customer;

import application.exceptions.businessexceptions.cartexceptions.CantRemoveFromCart;
import application.exceptions.businessexceptions.userexceptions.InvalidAccess;
import application.repositories.IBookRepository;
import application.repositories.IUserRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.Book;
import domain.entities.Customer;
import domain.entities.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemoveCartUseCase implements IUseCase {

	private final IUserRepository userRepository;
	private final IBookRepository bookRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.REMOVE_CART;
	}

	public Result<Customer> perform(String title, String username, User.Role role) {
		assert title != null && !title.isBlank() : "we relay on @NotBlank validation from presentation layer";
        if (!User.Role.CUSTOMER.equals(role))
            return Result.failure(new InvalidAccess("customer"));

		Result<User> userResult = userRepository.get(username);
		if (userResult.isFailure())
			return Result.failure(userResult.exception());
		assert userResult.data() instanceof Customer : "we relay on role passing from presentation layer";
		Customer customer = (Customer) userResult.data();

		Result<Book> bookResult = bookRepository.get(title);
		if (bookResult.isFailure())
			return Result.failure(bookResult.exception());
		Book book = bookResult.data();

		if (!customer.canRemoveBook(book))
			return Result.failure(new CantRemoveFromCart(customer.findRemoveBookErrors(book)));

		customer.removeBook(book);
		return Result.success(customer);
	}
}
