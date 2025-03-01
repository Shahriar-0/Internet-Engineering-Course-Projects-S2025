package domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import domain.entities.Book;
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
	private final List<Book> books;

	public PurchasedCart(Cart cart) {
		this.books = cart.getBooks();
		this.datePurchased = LocalDateTime.now();
		this.totalCost = books.stream().mapToLong(Book::getPrice).sum();
		this.bookCount = books.size();
	}
}
