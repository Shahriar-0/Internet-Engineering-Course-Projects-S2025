package domain.entities.booklicense;

import domain.entities.book.Book;
import domain.entities.user.Customer;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
public class ExpiringBookLicense extends BookLicense {
    public static final int MIN_VALIDITY_DAYS = 1;
    public static final int MAX_VALIDITY_DAYS = 10;

    private int validityDays;

    public ExpiringBookLicense(Customer customer, long id, Book book, long price, LocalDateTime purchaseDate, int validityDays) {
        super(customer, id, book, price, purchaseDate);
        this.validityDays = validityDays;
        assert validityDays <= MAX_VALIDITY_DAYS && validityDays >= MIN_VALIDITY_DAYS;
    }

    @Override
    public boolean isValid() {
        return !isExpired();
    }

    private boolean isExpired() {
        return LocalDateTime.now().isAfter(purchaseDate.plusDays(validityDays));
    }
}
