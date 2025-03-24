package domain.entities.cart;

import domain.entities.book.Book;
import domain.entities.DomainEntity;
import domain.entities.booklicense.BookLicense;
import domain.entities.booklicense.ExpiringBookLicense;
import domain.entities.booklicense.PermanentBookLicense;
import domain.entities.user.Customer;
import domain.exceptions.DomainException;
import domain.exceptions.cart.BookAlreadyInCart;
import domain.exceptions.cart.BookDoesNotExistInCart;
import domain.exceptions.cart.CartIsFull;
import domain.exceptions.cart.CustomerAlreadyHasAccess;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@SuperBuilder
public class Cart extends DomainEntity<Cart.Key> {
	public static final int MAXIMUM_BOOKS = 10;

	private final Customer customer;
	private final List<BookLicense> licenses = new ArrayList<>();
	private LocalDateTime purchaseDate;
	public String getCustomerName() {
		return key.customerName;
	}
	public Long getId() {
		return key.id;
	}

	public Cart(Customer customer, long id) {
		super(new Key(customer.getKey(), id));
		this.customer = customer;
	}


	public List<DomainException> getAddBookErrors(String bookTitle) {
        List<DomainException> exceptions = new ArrayList<>();
		if (licenses.size() >= MAXIMUM_BOOKS)
			exceptions.add(new CartIsFull());
		if (licenses.stream().anyMatch(b -> b.getBook().isKeyEqual(bookTitle)))
			exceptions.add(new BookAlreadyInCart(bookTitle));
		if (customer.hasAccess(bookTitle))
			exceptions.add(new CustomerAlreadyHasAccess(bookTitle));

        return exceptions;
	}

	public List<DomainException> getRemoveBookErrors(String bookTitle) {
		List<DomainException> exceptions = new ArrayList<>();
        if (licenses.stream().noneMatch(b -> b.getBook().getTitle().equals(bookTitle)))
			exceptions.add(new BookDoesNotExistInCart(bookTitle));

		return exceptions;
	}

	public void addBook(Book book) {
        assert getAddBookErrors(book.getTitle()).isEmpty();
		licenses.add(new PermanentBookLicense(book));
	}

	public void borrowBook(Book book, int borrowDays) {
        assert getAddBookErrors(book.getTitle()).isEmpty();
		licenses.add(new ExpiringBookLicense(book, borrowDays));
	}

	public void removeBook(Book book) {
        assert getRemoveBookErrors(book.getTitle()).isEmpty();
		licenses.removeIf(b -> b.getBook().isKeyEqual(book.getKey()));
	}

	public long getTotalCost() {
		return licenses.stream().mapToLong(BookLicense::getPrice).sum();
	}

	public void purchase() {
		assert purchaseDate == null;
		purchaseDate = LocalDateTime.now();
		licenses.forEach(l -> l.setPurchaseDate(purchaseDate));
	}

	public record Key(String customerName, long id) {};
}
