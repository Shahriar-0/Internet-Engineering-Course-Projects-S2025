package domain.entities.booklicense;

import domain.entities.DomainEntity;
import domain.entities.book.Book;
import domain.entities.user.Customer;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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

	public abstract boolean isValid();

	public record Key(String customerName, long id) {}
}
