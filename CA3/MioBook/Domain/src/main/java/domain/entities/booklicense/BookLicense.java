package domain.entities.booklicense;

import domain.entities.DomainEntity;
import domain.entities.book.Book;
import domain.entities.user.Customer;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
public abstract class BookLicense extends DomainEntity<BookLicense.Key> {
    protected Customer customer;
    protected Book book;
    protected long price;
    protected LocalDateTime purchaseDate;
    public String getCustomerName() {
        return key.customerName;
    }
    public long getId() {
        return key.id;
    }

    public BookLicense(Customer customer, long id, Book book, long price, LocalDateTime purchaseDate) {
        super(new Key(customer.getKey(), id));
        this.customer = customer;
        this.book = book;
        this.price = price;
        this.purchaseDate = purchaseDate;
    }

    public boolean isValid() {
        return purchaseDate != null;
    }

    public record Key(String customerName, long id) {} ;
}
