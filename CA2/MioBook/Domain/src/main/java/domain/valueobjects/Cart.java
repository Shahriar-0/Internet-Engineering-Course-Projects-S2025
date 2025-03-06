package domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import domain.entities.Book;
import domain.entities.Customer;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cart {

	@JsonIgnore
	private final Customer customer;

	@JsonIgnore
	private final List<CustomerBook> books;

	public Cart(Customer customer) {
		this.customer = customer;
		this.books = new ArrayList<>();
	}

	@JsonIgnore
	private final transient int MAXIMUM_BOOKS = 10;

	public String findAddBookErrors(Book book) {
		if (books.size() >= MAXIMUM_BOOKS)
			return "Cart is full! Cannot add more books. Maximum books: " + MAXIMUM_BOOKS;
		if (books.stream().anyMatch(b -> b.getBook().getTitle().equals(book.getTitle())))
			return ("Book with title '" + book.getTitle() + "' is already in cart!");
		return null;
	}

	public String findRemoveBookErrors(Book book) {
		if (!books.stream().anyMatch(b -> b.getBook().getTitle().equals(book.getTitle())))
			return ("Book with title '" + book.getTitle() + "' is not in cart!");
		return null;
	}

	public String findPurchaseCartErrors(long credit) {
		if (books.size() == 0)
			return "Cart is empty! Cannot purchase cart.";
		if (credit < getTotalCost())
			return (
				"Not enough credit! Cannot purchase cart. Required credit: " +
				books.stream().mapToLong(b -> b.getBook().getPrice()).sum() +
				", Current credit: " +
				credit
			);
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

	@JsonIgnore
	public int getMAXIMUM_BOOKS() {
		return MAXIMUM_BOOKS;
	}

	@JsonProperty("items")
	public List<CustomerBook> getBooks() {
		return books;
	}

	@JsonProperty("username")
	public String getUsername() {
		return customer.getUsername();
	}
}
