package domain.entities;

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

class Cart {

	private final List<Book> books = new ArrayList<>();
	private final int MAXIMUM_BOOKS = 10;

	// FIXME: I'm not sure about this choice
	// also the performce of this method is not good cause of duplication
	String getAddBookError(Book book) {
		if (books.size() >= MAXIMUM_BOOKS)
			return "Cart is full! Cannot add more books. Maximum books: " + MAXIMUM_BOOKS;
		return null;
	}

	String getRemoveBookError(Book book) {
		if (!books.stream().anyMatch(b -> b.getTitle().equals(book.getTitle())))
			return "Book with title '" + book.getTitle() + "' is not in cart!";
		return null;
	}

	Boolean canAddBook(Book book) {
		return books.size() < MAXIMUM_BOOKS;
	}

	Boolean canRemoveBook(Book book) {
		return books.stream().anyMatch(b -> b.getTitle().equals(book.getTitle()));
	}

	void addBook(Book book) {
		books.add(book);
	}

	void removeBook(String title) {
		books.removeIf(book -> book.getTitle().equals(title));
	}
}
