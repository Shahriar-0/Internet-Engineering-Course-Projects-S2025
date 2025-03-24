package domain.entities.cart;

import domain.entities.book.Book;
import domain.entities.DomainEntity;
import domain.entities.booklicense.BookLicense;
import domain.entities.booklicense.ExpiringBookLicense;
import domain.entities.booklicense.PermanentBookLicense;
import domain.entities.user.Customer;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@SuperBuilder
public class Cart extends DomainEntity<Cart.Key> {
	private static final int MAXIMUM_BOOKS = 10;

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


	public String findAddBookErrors(Book book) {
		if (licenses.size() >= MAXIMUM_BOOKS)
			return "Cart is full! Cannot add more books. Maximum books: " + MAXIMUM_BOOKS;
		if (licenses.stream().anyMatch(b -> b.getBook().getTitle().equals(book.getTitle())))
			return ("Book with title '" + book.getTitle() + "' is already in cart!");
		if (customer.hasAccess(book))
			return ("Book with title '" + book.getTitle() + "' has already been bought!");
		return null;
	}

	public String findRemoveBookErrors(Book book) {
		if (licenses.stream().noneMatch(b -> b.getBook().getTitle().equals(book.getTitle())))
			return ("Book with title '" + book.getTitle() + "' is not in cart!");
		return null;
	}

	public String findPurchaseCartErrors(long credit) {
		if (licenses.isEmpty())
			return "Cart is empty!";
		if (credit < getTotalCost())
			return (
				"Not enough credit! Required credit: " +
				getTotalCost() +
				", Current credit: " +
				credit
			);
		return null;
	}

	public void addBook(Book book) {
		licenses.add(new PermanentBookLicense(book));
	}

	public void borrowBook(Book book, int borrowDays) {
		licenses.add(new ExpiringBookLicense(book, borrowDays));
	}

	public void removeBook(Book book) {
		licenses.removeIf(b -> b.getBook().getTitle().equals(book.getTitle()));
	}

	public long getTotalCost() {
		return licenses.stream().mapToLong(BookLicense::getPrice).sum();
	}

	public void purchase() {
		assert purchaseDate == null;
		purchaseDate = LocalDateTime.now();
		licenses.forEach(l -> l.setPurchaseDate(purchaseDate));
	}

	public String getUsername() {
		return customer.getUsername();
	}

	public record Key(String customerName, long id) {};
}
