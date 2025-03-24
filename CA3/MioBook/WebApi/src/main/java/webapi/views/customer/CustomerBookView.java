package webapi.views.customer;

import domain.entities.booklicense.BookLicense;
import domain.entities.booklicense.ExpiringBookLicense;

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
			license.getBook().getAuthorName(),
			license.getBook().getPublisher(),
			license.getBook().getGenres(),
			license.getBook().getPublishedYear(),
			license.getBook().getPrice(),
			license instanceof ExpiringBookLicense,
			(license instanceof ExpiringBookLicense el) ? el.getValidityDays() : null,
			license.getPrice()
		);
	}
}
