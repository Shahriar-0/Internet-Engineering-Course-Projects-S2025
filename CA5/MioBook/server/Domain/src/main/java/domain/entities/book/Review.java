package domain.entities.book;

import domain.entities.DomainEntity;
import domain.entities.user.Customer;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class Review extends DomainEntity {

	public static final int MIN_RATING_NUMBER = 1;
	public static final int MAX_RATING_NUMBER = 5;

	private final Integer rating;
	private final String comment;
	private final Customer customer;
	private final Book book;
	private final LocalDateTime dateTime;

	public Review(int rating, String comment, Customer customer, Book book) {
		this.rating = rating;
		this.comment = comment;
		this.customer = customer;
		this.book = book;
		this.dateTime = LocalDateTime.now();
		assert this.rating <= MAX_RATING_NUMBER && this.rating >= MIN_RATING_NUMBER;
	}
}
