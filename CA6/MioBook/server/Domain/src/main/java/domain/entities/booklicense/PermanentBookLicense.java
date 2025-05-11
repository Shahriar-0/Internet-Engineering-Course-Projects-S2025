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
public class PermanentBookLicense extends BookLicense {
    public PermanentBookLicense(Customer customer, Book book, long price, LocalDateTime purchaseDate) {
        super(customer, book, price, purchaseDate);
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
