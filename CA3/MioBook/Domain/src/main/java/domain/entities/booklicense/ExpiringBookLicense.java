package domain.entities.booklicense;

import domain.entities.book.Book;
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

    public ExpiringBookLicense(Book book, int validityDays) {
        super(book, calcLicensePrice(book.getPrice(), validityDays));
        this.validityDays = validityDays;
        assert validityDays <= MAX_VALIDITY_DAYS && validityDays >= MIN_VALIDITY_DAYS;
    }

    @Override
    public boolean isValid() {
        return super.isValid() && !isExpired();
    }

    private boolean isExpired() {
        assert purchaseDate != null;
        return LocalDateTime.now().isAfter(purchaseDate.plusDays(validityDays));
    }

    private static long calcLicensePrice(long basePrice, int validityDays) {
        return basePrice * validityDays / MAX_VALIDITY_DAYS;
    }
}
