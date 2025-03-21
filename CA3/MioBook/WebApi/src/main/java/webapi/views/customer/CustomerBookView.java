package webapi.views.customer;

import domain.valueobjects.CustomerBook;

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
	public CustomerBookView(CustomerBook book) {
		this(
			book.getBook().getTitle(),
			book.getBook().getAuthorName(),
			book.getBook().getPublisher(),
			book.getBook().getGenres(),
			book.getBook().getYear(),
			book.getBook().getPrice(),
			book.getIsBorrowed(),
			book.getBorrowDays(),
			book.getFinalPrice()
		);
	}
}
