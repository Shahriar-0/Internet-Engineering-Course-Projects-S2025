package domain.valueobjects;

import domain.entities.Book;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CustomerBook {

	private final Book book;
	private final Boolean isBorrowed;
	private final int borrowDays;
	private final long price;
	private final transient int MAXIMUM_BORROW_DAYS = 10;

	public CustomerBook(Book book, int borrowDays) {
		if (borrowDays > MAXIMUM_BORROW_DAYS)
            throw new RuntimeException("Borrow days cannot exceed " + MAXIMUM_BORROW_DAYS);
		if (borrowDays < 0)
            throw new RuntimeException("Borrow days cannot be negative");

		this.book = book;
		this.isBorrowed = borrowDays > 0;
		this.borrowDays = borrowDays;
		this.price = book.getPrice() * borrowDays / MAXIMUM_BORROW_DAYS;
	}

	public CustomerBook(Book book) {
		this.book = book;
		this.isBorrowed = false;
		this.borrowDays = 0;
		this.price = book.getPrice();
	}

	public long getFinalPrice() {
		return price;
	}

	public int getMAXIMUM_BORROW_DAYS() {
		return MAXIMUM_BORROW_DAYS;
	}

	public Boolean isStillAccessible(LocalDateTime purchaseDate) {
		return !isBorrowed || LocalDateTime.now().isBefore(purchaseDate.plusDays(borrowDays));
	}
}
