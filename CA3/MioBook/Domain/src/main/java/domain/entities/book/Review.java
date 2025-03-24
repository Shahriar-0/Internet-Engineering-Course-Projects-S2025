package domain.entities.book;

import domain.entities.DomainEntity;
import domain.entities.user.Customer;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@SuperBuilder
public class Review extends DomainEntity<Review.Key> {
	public static final int MIN_RATING_NUMBER = 1;
	public static final int MAX_RATING_NUMBER = 5;

	private final int rating;
	private final String comment;
	private final Customer customer;
	private final Book book;
	private final LocalDateTime dateTime;
	public String getCustomerName() {
		return key.customerName;
	}
	public String getBookTitle() {
		return key.bookTitle;
	}

	public Review(int rating, String comment, Customer customer, Book book, LocalDateTime dateTime) {
		super(new Key(customer.getKey(), book.getKey()));
		this.rating = rating;
		this.comment = comment;
		this.customer = customer;
		this.book = book;
		this.dateTime = dateTime;
		assert this.rating <= MAX_RATING_NUMBER && this.rating >= MIN_RATING_NUMBER;
	}

	public record Key(String customerName, String bookTitle){};
}
