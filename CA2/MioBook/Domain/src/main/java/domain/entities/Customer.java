package domain.entities;

import domain.valueobjects.Cart;
import domain.valueobjects.PurchasedCart;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Customer extends User {

	@Builder.Default
	private Cart cart = new Cart();

	@Builder.Default
	private List<PurchasedCart> purchasedCarts = new ArrayList<>();

	public Boolean canAddBook(Book book) {
		return cart.getAddBookError(book) == null;
	}

	public Boolean canRemoveBook(Book book) {
		return cart.getRemoveBookError(book) == null;
	}

	public Boolean canPurchaseCart() {
		return cart.getPurchaseCartError(credit) == null;
	}

	/**
	 * @param book The book to be added to the cart.
	 * @return A string containing the reason why the book cannot be added to the cart.
	 *         If the book can be added, this method returns null.
	 */
	public String getAddBookError(Book book) {
		return cart.getAddBookError(book);
	}

	/**
	 * @return A string containing the reason why a book cannot be removed from the cart.
	 *         If a book can be removed, this method returns null.
	 */
	public String getRemoveBookError(Book book) {
		return cart.getRemoveBookError(book);
	}

	/**
	 * @return A string containing the reason why the cart cannot be purchased.
	 *         If the cart can be purchased, this method returns null.
	 */
	public String getPurchaseCartError() {
		return cart.getPurchaseCartError(credit);
	}

	// The validations in these methods are just to make them robust, they should never be called
	public void addBook(Book book) {
		if (!canAddBook(book))
			throw new RuntimeException(getAddBookError(book));

		cart.addBook(book);
	}

	public void removeBook(Book book) {
		if (!canRemoveBook(book))
			throw new RuntimeException(getRemoveBookError(book));

		cart.removeBook(book);
	}

	public void addCredit(long amount) {
		if (amount < 0)
			throw new RuntimeException("Amount must be positive.");

		credit += amount;
	}

	public PurchasedCart purchaseCart() {
		if (!canPurchaseCart())
			throw new RuntimeException(getPurchaseCartError());

		purchasedCarts.add(new PurchasedCart(cart));
		credit -= cart.getBooks().stream().mapToLong(Book::getPrice).sum();
		cart.emptyCart();
		return purchasedCarts.get(purchasedCarts.size() - 1);
	}
}
