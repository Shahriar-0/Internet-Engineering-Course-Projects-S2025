package application.usecase.customer.cart;

import application.exceptions.businessexceptions.bookexceptions.BookDoesntExist;
import application.exceptions.businessexceptions.cartexceptions.CantAddToCart;
import application.repositories.IBookRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.Book;
import domain.entities.Customer;
import domain.entities.User;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddCart implements IUseCase {
	private final IBookRepository bookRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.ADD_CART;
	}

	public Result<Customer> perform(AddCartData data, User user) {
		assert user instanceof Customer: "we rely on presentation layer access control";
		Customer customer = (Customer) user;

		Result<Book> bookResult = bookRepository.get(data.title);
		if (bookResult.isFailure())
			return Result.failure(new BookDoesntExist(data.title));
		Book book = bookResult.data();

		if (!customer.canAddBook(book))
			return Result.failure(new CantAddToCart(customer.findAddBookErrors(book)));

		customer.addBook(book);
		return Result.success(customer);
	}

	public record AddCartData(
    	@NotBlank String title
  	) {}
}
