package domain.entities;

import domain.valueobjects.Cart;
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

	public Boolean canAddBook(Book book) {
		return cart.canAddBook(book);
	}

	public Boolean canRemoveBook(Book book) {
		return cart.canRemoveBook(book);
	}

	/**
	 * @return A string containing the reason why a book cannot be added to the cart. If a book can be added, this method returns null.
	 */
	public String getAddBookError(Book book) {
		return cart.getAddBookError(book);
	}

	/**
	 * @return A string containing the reason why a book cannot be removed from the cart. If a book can be removed, this method returns null.
	 */
	public String getRemoveBookError(Book book) {
		return cart.getRemoveBookError(book);
	}

	public void addBook(Book book) {
		cart.addBook(book);
	}

	public void removeBook(String title) {
		cart.removeBook(title);
	}

	public void addCredit(long amount) {
		credit += amount;
	}
}


