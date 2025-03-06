package domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import domain.valueobjects.Cart;
import domain.valueobjects.PurchaseHistory;
import domain.valueobjects.PurchasedBooks;
import domain.valueobjects.PurchasedCart;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Customer extends User {

	@JsonIgnore
	private Cart cart;

	@JsonIgnore
	private PurchaseHistory purchaseHistory;

	protected Customer(CustomerBuilder<?, ?> builder) {
        super(builder);
        this.cart = new Cart(this);
		this.purchaseHistory = new PurchaseHistory(this);
    }

	@JsonIgnore
	private long credit;

	@JsonProperty("balance")
	public long getBalance() {
		return credit;
	}

	@JsonIgnore
	public PurchasedBooks getPurchasedBooks() {
		return new PurchasedBooks(this);
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

	public PurchasedCart purchaseCart() {
		if (!canPurchaseCart())
			throw new RuntimeException(findPurchaseCartErrors());

		PurchasedCart purchasedCart = purchaseHistory.addPurchasedCart(new PurchasedCart(cart));
		credit -= cart.getTotalCost();
		cart.emptyCart();
		return purchasedCart;
	}
}
