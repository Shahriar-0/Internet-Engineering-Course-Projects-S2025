package domain.valueobjects;

import domain.entities.Customer;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class PurchasedBooks {

	private final Customer customer;
	private final List<CustomerBook> books;

	public PurchasedBooks(Customer customer) {
		this.customer = customer;
		LocalDateTime now = LocalDateTime.now();
		this.books =
			customer
				.getPurchaseHistory()
				.getPurchasedCarts()
				.stream()
				.flatMap(ph -> ph.getBooks().stream())
				.filter(b -> b.isStillAccessible(now))
				.toList();
	}

	public String getUsername() {
		return customer.getUsername();
	}

	public long getTotalCost() {
		return books.stream().mapToLong(CustomerBook::getFinalPrice).sum();
	}

	public BookContent getBookContent(String title) {
		return books.stream().filter(b -> b.getBook().getTitle().equals(title)).findFirst().get().getBook().getContent();
	}
}
