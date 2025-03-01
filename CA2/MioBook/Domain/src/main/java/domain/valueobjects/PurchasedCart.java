package domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;

@Getter
public class PurchasedCart {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private final LocalDateTime datePurchased;

	private final long totalCost;

	private final int bookCount;

	@JsonIgnore
	private final List<CustomerBook> books;

	public PurchasedCart(Cart cart) {
		this.books = cart.getBooks();
		this.datePurchased = LocalDateTime.now();
		this.totalCost = cart.getTotalCost();
		this.bookCount = books.size();
	}
}
