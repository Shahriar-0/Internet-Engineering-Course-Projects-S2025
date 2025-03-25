package webapi.views.book;

import application.pagination.Page;
import domain.entities.book.Book;

import java.util.List;

public record BookView(
	String title,
	String author,
	String publisher,
	List<String> genres,
	Integer year,
	Long price,
	String synopsis,
	Float averageRating
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
			entity.getAverageRating()
		);
	}

	public static Page<BookView> mapToView(Page<Book> bookPage) {
		return Page
			.<BookView>builder()
			.list(bookPage.getList().stream().map(BookView::new).toList())
			.pageNumber(bookPage.getPageNumber())
			.pageSize(bookPage.getPageSize())
			.totalPageNumber(bookPage.getTotalPageNumber())
			.totalDataSize(bookPage.getTotalDataSize())
			.build();
	}
}
