package application.usecase.customer.cart;

import application.exceptions.businessexceptions.cartexceptions.CantRemoveFromCart;
import application.repositories.IBookRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.Book;
import domain.entities.Customer;
import domain.entities.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemoveCart implements IUseCase {
	private final IBookRepository bookRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.REMOVE_CART;
	}

	public Result<Customer> perform(String title, User user) {
		assert title != null && !title.isBlank() : "we rely on presentation layer validation for field 'title'";

		assert user instanceof Customer: "we rely on presentation layer access control";
		Customer customer = (Customer) user;

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
