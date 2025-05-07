package application.usecase.customer.cart;

import application.exceptions.businessexceptions.bookexceptions.BookDoesntExist;
import application.exceptions.businessexceptions.cartexceptions.CantAddToCart;
import application.repositories.IBookRepository;
import application.repositories.IUserRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.book.Book;
import domain.entities.cart.CartItem;
import domain.entities.user.Customer;
import domain.entities.user.User;
import domain.exceptions.DomainException;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddCart implements IUseCase {

	private final IBookRepository bookRepository;
	private final IUserRepository userRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.ADD_CART;
	}

	public Result<Customer> perform(AddCartData data, User user) {
		assert user instanceof Customer: "we rely on presentation layer access control";
		Customer customer = (Customer) user;

		Optional<Book> bookResult = bookRepository.findByTitle(data.title);
		if (bookResult.isEmpty())
			return Result.failure(new BookDoesntExist(data.title));
		Book book = bookResult.get();

       List<DomainException> exceptions = customer.getCart().getAddBookErrors(data.title);
		if (!exceptions.isEmpty())
			return Result.failure(new CantAddToCart(exceptions.getFirst().getMessage()));

		CartItem cartItem = customer.getCart().addBook(book);
		userRepository.saveCart(cartItem);
		return Result.success(customer);
	}

	public record AddCartData(
    	@NotBlank String title
  	) {}
}
