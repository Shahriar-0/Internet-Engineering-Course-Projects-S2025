package domain.valueobjects;

import java.util.ArrayList;
import java.util.List;

import domain.entities.Book;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart {

	private final List<CustomerBook> books = new ArrayList<>();
	private final int MAXIMUM_BOOKS = 10;

	public String getAddBookError(Book book) {
		if (books.size() >= MAXIMUM_BOOKS)
			return "Cart is full! Cannot add more books. Maximum books: " + MAXIMUM_BOOKS;
		return null;
	}

	public String getRemoveBookError(Book book) {
		if (!books.stream().anyMatch(b -> b.getBook().getTitle().equals(book.getTitle())))
			return "Book with title '" + book.getTitle() + "' is not in cart!";
		return null;
	}

    public String getPurchaseCartError(long credit) {
        if (books.size() == 0)
            return "Cart is empty! Cannot purchase cart.";
        if (credit < books.stream().mapToLong(b -> b.getBook().getPrice()).sum())
            return "Not enough credit! Cannot purchase cart.";
        return null;
    }

	public void addBook(Book book) {
		books.add(new CustomerBook(book));
	}

	public void borrowBook(Book book, int borrowDays) {
		books.add(new CustomerBook(book, borrowDays));
	}

	public void removeBook(Book book) {
        books.removeIf(b -> b.getBook().getTitle().equals(book.getTitle()));
	}

	public long getTotalCost() {
		return books.stream().mapToLong(b -> b.getPrice()).sum();
	}

    public void emptyCart() {
        books.clear();
    }
}
