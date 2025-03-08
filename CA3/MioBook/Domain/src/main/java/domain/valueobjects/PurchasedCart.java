package domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class PurchasedCart {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private final LocalDateTime datePurchased;

	private final long totalCost;

	private final List<CustomerBook> books;

	public PurchasedCart(Cart cart) {
		this.books = new ArrayList<>(cart.getBooks());;
		this.datePurchased = LocalDateTime.now();
		this.totalCost = cart.getTotalCost();
	}

	public Boolean hasBook(String title) {
		return books.stream().filter(b -> b.isStillAccessible(LocalDateTime.now())).anyMatch(b -> b.getBook().getTitle().equals(title));
	}
}
