package domain.entities.cart;

import domain.entities.DomainEntity;
import domain.entities.book.Book;
import domain.entities.user.Customer;
import domain.exceptions.DomainException;
import domain.exceptions.cart.BookAlreadyInCart;
import domain.exceptions.cart.BookDoesNotExistInCart;
import domain.exceptions.cart.CartIsFull;
import domain.exceptions.cart.CustomerAlreadyHasAccess;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Cart extends DomainEntity {

	public static final int MAXIMUM_BOOKS = 10;

	private final Customer customer;
	private final List<CartItem> items = new ArrayList<>();

	public Cart(Customer customer) {
		super(customer.getId());
		this.customer = customer;
	}

	public List<DomainException> getAddBookErrors(String bookTitle) {
		List<DomainException> exceptions = new ArrayList<>();
		if (items.size() >= MAXIMUM_BOOKS)
			exceptions.add(new CartIsFull());
		if (items.stream().anyMatch(b -> b.getBook().isTitleEqual(bookTitle)))
			exceptions.add(new BookAlreadyInCart(bookTitle));
		if (customer.hasAccess(bookTitle))
			exceptions.add(new CustomerAlreadyHasAccess(bookTitle));

		return exceptions;
	}

	public List<DomainException> getRemoveBookErrors(String bookTitle) {
		List<DomainException> exceptions = new ArrayList<>();
		if (items.stream().noneMatch(b -> b.getBook().isTitleEqual(bookTitle)))
			exceptions.add(new BookDoesNotExistInCart(bookTitle));

		return exceptions;
	}

	public void addBook(Book book) {
		assert getAddBookErrors(book.getTitle()).isEmpty();
		items.add(CartItem.createPermanentItem(this, book));
	}

	public void borrowBook(Book book, int borrowDays) {
		assert getAddBookErrors(book.getTitle()).isEmpty();
		items.add(CartItem.createBorrowingItem(this, book, borrowDays));
	}

	public void removeBook(Book book) {
		assert getRemoveBookErrors(book.getTitle()).isEmpty();
		items.removeIf(b -> b.getBook().equals(book));
	}

	public long getTotalCost() {
		return items.stream().mapToLong(CartItem::getPrice).sum();
	}
}
