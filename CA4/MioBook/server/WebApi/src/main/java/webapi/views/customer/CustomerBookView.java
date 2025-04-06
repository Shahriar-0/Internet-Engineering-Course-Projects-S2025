package webapi.views.customer;

import domain.entities.booklicense.BookLicense;
import domain.entities.booklicense.TemporaryBookLicense;
import domain.entities.cart.CartItem;
import domain.entities.cart.PurchasedCartItem;
import java.util.List;

public record CustomerBookView(
	String title,
	String author,
	String publisher,
	List<String> genres,
	Integer year,
	Long price,
	Boolean isBorrowed,
	Integer borrowDays,
	Long finalPrice
) {
	public CustomerBookView(BookLicense license) {
		this(
			license.getBook().getTitle(),
			license.getBook().getAuthor().getName(),
			license.getBook().getPublisher(),
			license.getBook().getGenres(),
			license.getBook().getPublishedYear(),
			license.getBook().getBasePrice(),
			license instanceof TemporaryBookLicense,
			(license instanceof TemporaryBookLicense el) ? el.getValidityDays() : null,
			license.getPrice()
		);
	}

	public CustomerBookView(PurchasedCartItem item) {
		this(
			item.getBook().getTitle(),
			item.getBook().getAuthor().getName(),
			item.getBook().getPublisher(),
			item.getBook().getGenres(),
			item.getBook().getPublishedYear(),
			item.getBook().getBasePrice(),
			item.isBorrow(),
			item.isBorrow() ? item.getBorrowDays() : null,
			item.getPrice()
		);
	}

	public CustomerBookView(CartItem item) {
		this(
			item.getBook().getTitle(),
			item.getBook().getAuthor().getName(),
			item.getBook().getPublisher(),
			item.getBook().getGenres(),
			item.getBook().getPublishedYear(),
			item.getBook().getBasePrice(),
			item.isBorrow(),
			item.isBorrow() ? item.getBorrowDays() : null,
			item.getPrice()
		);
	}
}
