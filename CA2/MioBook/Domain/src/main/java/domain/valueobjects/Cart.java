package domain.valueobjects;

import java.util.ArrayList;
import java.util.List;

import domain.entities.Book;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart {

	private final List<Book> books = new ArrayList<>();
	private final int MAXIMUM_BOOKS = 10;

	// FIXME: I'm not sure about this choice
	// also the performce of this method is not good cause of duplication
	public String getAddBookError(Book book) {
		if (books.size() >= MAXIMUM_BOOKS)
			return "Cart is full! Cannot add more books. Maximum books: " + MAXIMUM_BOOKS;
		return null;
	}

	public String getRemoveBookError(Book book) {
		if (!books.stream().anyMatch(b -> b.getTitle().equals(book.getTitle())))
			return "Book with title '" + book.getTitle() + "' is not in cart!";
		return null;
	}

	public Boolean canAddBook(Book book) {
		return books.size() < MAXIMUM_BOOKS;
	}

	public Boolean canRemoveBook(Book book) {
		return books.stream().anyMatch(b -> b.getTitle().equals(book.getTitle()));
	}

	public void addBook(Book book) {
		books.add(book);
	}

	public void removeBook(String title) {
		books.removeIf(book -> book.getTitle().equals(title));
	}
}
