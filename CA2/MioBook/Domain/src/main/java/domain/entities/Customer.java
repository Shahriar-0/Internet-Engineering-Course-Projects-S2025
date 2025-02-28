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

	/**
	 * @return A string containing the reason why a book cannot be added to the cart. If a book can be added, this method returns null.
	 */
	public String getAddBookError() {
		return cart.getAddBookError();
	}

	public void addBook(Book book) {
		cart.addBook(book);
	}
}

class Cart {

	private final List<Book> books = new ArrayList<>();
	private final int MAXIMUM_BOOKS = 10;

	String getAddBookError() { // FIXME: I'm not sure about this choice
							   // also the performce of this method is not good cause of duplication
		if (books.size() >= MAXIMUM_BOOKS)
			return "Cart is full! Cannot add more books. Maximum books: " + MAXIMUM_BOOKS;
		return null;
	}

	Boolean canAddBook(Book book) {
		return books.size() < MAXIMUM_BOOKS;
	}

	void addBook(Book book) {
		books.add(book);
	}
}
