package webapi.views.book;

import domain.entities.book.Book;
import org.springframework.data.domain.Page;

import java.util.List;

public record BookView(
	String title,
	String author,
	String publisher,
	List<String> genres,
	Integer year,
	Long price,
	String synopsis,
	Float averageRating,
	String cover
) {
	public BookView(Book entity) {
		this(
			entity.getTitle(),
			entity.getAuthor().getName(),
			entity.getPublisher(),
			entity.getGenres(),
			entity.getPublishedYear(),
			entity.getBasePrice(),
			entity.getSynopsis(),
			entity.getAverageRating(),
			entity.getCoverLink()
		);
	}

	public static Page<BookView> mapToView(Page<Book> bookPage) {
		return bookPage.map(BookView::new);
	}
}
