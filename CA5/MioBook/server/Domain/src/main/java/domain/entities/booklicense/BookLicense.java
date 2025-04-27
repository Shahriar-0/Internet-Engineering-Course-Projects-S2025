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
public abstract class BookLicense extends DomainEntity {

	protected Customer customer;
	protected Book book;
	protected Long price;
	protected LocalDateTime purchaseDate;

	public BookLicense(Customer customer, Book book, long price, LocalDateTime purchaseDate) {
		this.customer = customer;
		this.book = book;
		this.price = price;
		this.purchaseDate = purchaseDate;
	}

	public abstract boolean isValid();
}
