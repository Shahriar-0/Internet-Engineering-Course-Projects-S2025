package domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import domain.entities.Customer;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class PurchasedBooks {

	@JsonIgnore
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

	@JsonProperty("username")
	public String getUsername() {
		return customer.getUsername();
	}

	@JsonIgnore // Add this annotation to exclude totalCost from serialization
	public long getTotalCost() {
		return books.stream().mapToLong(CustomerBook::getFinalPrice).sum();
	}

	public BookContent getBookContent(String title) {
		return books.stream().filter(b -> b.getBook().getTitle().equals(title)).findFirst().get().getBook().getContent();
	}
}
