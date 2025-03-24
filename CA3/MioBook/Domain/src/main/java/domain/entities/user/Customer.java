package domain.entities.user;

import domain.entities.book.Book;
import domain.entities.booklicense.BookLicense;
import domain.entities.cart.Cart;
import domain.valueobjects.Address;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
public class Customer extends User {
	private static final int FIRST_CART_ID = 1;
	public static final int INITIAL_CREDIT_AMOUNT = 0;

	private long credit;
	private Cart cart;
	private final List<BookLicense> purchasedLicenses = new ArrayList<>();
	private final List<Cart> purchaseHistory = new ArrayList<>();

	public Customer(String username, String password, String email, Address address) {
		super(username, password, email, address, Role.CUSTOMER);
		this.cart = new Cart(this, 1);
		this.credit = 0;
	}

	public List<BookLicense> getValidLicenses() {
		return purchasedLicenses.stream()
				.filter(BookLicense::isValid)
				.toList();
	}

	public Boolean canAddBook(Book book) {
		return cart.findAddBookErrors(book) == null;
	}

	public Boolean canRemoveBook(Book book) {
		return cart.findRemoveBookErrors(book) == null;
	}

	public Boolean canPurchaseCart() {
		return cart.findPurchaseCartErrors(credit) == null;
	}

	/**
	 * @param book The book to be added to the cart.
	 * @return A string containing the reason why the book cannot be added to the cart.
	 *         If the book can be added, this method returns null.
	 */
	public String findAddBookErrors(Book book) {
		return cart.findAddBookErrors(book);
	}

	/**
	 * @return A string containing the reason why a book cannot be removed from the cart.
	 *         If a book can be removed, this method returns null.
	 */
	public String findRemoveBookErrors(Book book) {
		return cart.findRemoveBookErrors(book);
	}

	/**
	 * @return A string containing the reason why the cart cannot be purchased.
	 *         If the cart can be purchased, this method returns null.
	 */
	public String findPurchaseCartErrors() {
		return cart.findPurchaseCartErrors(credit);
	}

	// the validations in these methods are just to make them robust, they should never be called
	public void addBook(Book book) {
		if (!canAddBook(book))
			throw new RuntimeException(findAddBookErrors(book));

		cart.addBook(book);
	}

	public void borrowBook(Book book, int borrowDays) {
		if (!canAddBook(book))
			throw new RuntimeException(findAddBookErrors(book)); // for now their validations are the same

		cart.borrowBook(book, borrowDays);
	}

	public void removeBook(Book book) {
		if (!canRemoveBook(book))
			throw new RuntimeException(findRemoveBookErrors(book));

		cart.removeBook(book);
	}

	public void addCredit(long amount) {
		if (amount < 0)
			throw new RuntimeException("Amount must be positive.");

		credit += amount;
	}

	public Cart purchaseCart() {
		if (!canPurchaseCart())
			throw new RuntimeException(findPurchaseCartErrors());

		credit -= cart.getTotalCost();
		cart.purchase();
		Cart purchasedCart = cart;
		cart = new Cart(this, purchaseHistory.size() + 1);
		purchaseHistory.add(purchasedCart);
		return purchasedCart;
	}

	/**
	 * Checks if the customer has previously purchased a book with the given title and is still accessible.
	 *
	 * @param book The book to check if it has been bought.
	 * @return True if the customer has bought the book, otherwise false.
	 */
	public Boolean hasAccess(Book book) {
		for (BookLicense license : purchasedLicenses)
			if (license.getBook().getTitle().equals(book.getTitle()))
				return license.isValid();

		return false;
	}
}
