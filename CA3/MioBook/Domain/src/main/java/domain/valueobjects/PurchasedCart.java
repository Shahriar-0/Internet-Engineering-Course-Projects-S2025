package domain.valueobjects;

import lombok.Getter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// TODO: remove this and merge it with cart
@Getter
public class PurchasedCart {

	private final LocalDateTime datePurchased;
	private final long totalCost;
	private final List<CustomerBook> books;

	public PurchasedCart(Cart cart) {
		this.books = new ArrayList<>(cart.getBooks());
		this.datePurchased = LocalDateTime.now();
		this.totalCost = cart.getTotalCost();
	}

	public Boolean hasBook(String title) {
		return books.stream().filter(
			b -> b.isStillAccessible(datePurchased)).anyMatch(b -> b.getBook().getTitle().equals(title)
		);
	}

	public List<CustomerBook> getAccessibleBooks() {
		return books.stream().filter(b -> b.isStillAccessible(datePurchased)).toList();
	}
}
