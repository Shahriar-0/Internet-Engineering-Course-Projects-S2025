package domain.entities;

import domain.valueobjects.Address;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class User extends DomainEntity<String> {

	public enum Role {
		CUSTOMER,
		ADMIN,
	}

	private Address address;
	private String password;
	private String email;
	private Role role;
	private long credit;
	private Cart cart;

	public Boolean canAddBook(Book book) {
		return cart.canAddBook(book);
	}

	public String getAddBookError() {
		return cart.getAddBookError();
	}

	public void addBook(Book book) {
		cart.addBook(book);
	}

	public String getUsername() {
		return super.getKey();
	}
}

class Cart {

	private final List<Book> books = new ArrayList<>();
	private final int MAXIMUM_BOOKS = 10;

	String getAddBookError() { // FIXME: I'm not sure about this choice
							   //also the performce of this method is not good cause of duplication
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
