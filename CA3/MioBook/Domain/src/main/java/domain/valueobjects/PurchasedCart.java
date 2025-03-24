package domain.valueobjects;

import domain.entities.booklicense.BookLicense;
import domain.entities.cart.Cart;
import lombok.Getter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// TODO: remove this and merge it with cart
@Getter
public class PurchasedCart {

	private final LocalDateTime datePurchased;
	private final long totalCost;
	private final List<BookLicense> books;

	public PurchasedCart(Cart cart) {
		this.books = new ArrayList<>(cart.getLicenses());
		this.datePurchased = LocalDateTime.now();
		this.totalCost = cart.getTotalCost();
	}

	public Boolean hasBook(String title) {
		return books.stream().filter(
                BookLicense::isValid).anyMatch(b -> b.getBook().getTitle().equals(title)
		);
	}

	public List<BookLicense> getAccessibleBooks() {
		return books.stream().filter(BookLicense::isValid).toList();
	}
}
