package domain.entities.booklicense;

import domain.entities.book.Book;
import domain.entities.user.Customer;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class TemporaryBookLicense extends BookLicense {

	public static final int MIN_VALIDITY_DAYS = 1;
	public static final int MAX_VALIDITY_DAYS = 10;

	private int validityDays;

	public TemporaryBookLicense(Customer customer, Book book, long price, LocalDateTime purchaseDate, int validityDays) {
		super(customer, book, price, purchaseDate);
		assert validityDays <= MAX_VALIDITY_DAYS && validityDays >= MIN_VALIDITY_DAYS;
		this.validityDays = validityDays;
	}

	@Override
	public boolean isValid() {
		return !isExpired();
	}

	private boolean isExpired() {
		return LocalDateTime.now().isAfter(purchaseDate.plusDays(validityDays));
	}
}
