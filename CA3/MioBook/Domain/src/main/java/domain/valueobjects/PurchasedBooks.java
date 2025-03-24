package domain.valueobjects;

import domain.entities.booklicense.BookLicense;
import domain.entities.user.Customer;
import lombok.Getter;
import java.util.List;

// TODO: move this to view
@Getter
public class PurchasedBooks {

	private final Customer customer;
	private final List<BookLicense> books;

	public PurchasedBooks(Customer customer) {
		this.customer = customer;
		this.books = customer.getPurchaseHistory().getAccessibleBooks();
	}

	public String getUsername() {
		return customer.getUsername();
	}

	public long getTotalCost() {
		return books.stream().mapToLong(BookLicense::getPrice).sum();
	}

	public BookContent getBookContent(String title) {
		return books.stream().filter(b -> b.getBook().getTitle().equals(title)).findFirst().get().getBook().getContent();
	}
}
