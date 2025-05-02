package application.usecase.customer.cart;

import application.exceptions.businessexceptions.cartexceptions.CantRemoveFromCart;
import application.repositories.IBookRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.book.Book;
import domain.entities.user.Customer;
import domain.entities.user.User;
import domain.exceptions.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

		Result<Book> bookResult = bookRepository.findByTitle(title);
		if (bookResult.isFailure())
			return Result.failure(bookResult.exception());
		Book book = bookResult.data();

        List<DomainException> exceptions = customer.getCart().getRemoveBookErrors(title);
		if (!exceptions.isEmpty())
			return Result.failure(new CantRemoveFromCart(exceptions.getFirst().getMessage()));

		customer.getCart().removeBook(book);
		return Result.success(customer);
	}
}
