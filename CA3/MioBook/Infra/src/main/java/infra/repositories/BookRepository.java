package infra.repositories;

import application.pagination.Page;
import application.repositories.IBookRepository;
import application.result.Result;
import application.usecase.user.book.GetBook;
import application.usecase.user.book.GetBookReviews;
import domain.entities.Book;
import domain.valueobjects.Review;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BookRepository extends BaseRepository<String, Book> implements IBookRepository {

	@Override
	protected Class<Book> getEntityClassType() {
		return Book.class;
	}

	@Override
	protected Book copyOf(Book persistedEntity) {
		return Book
			.builder()
			.key(persistedEntity.getTitle())
			.author(persistedEntity.getAuthor())
			.publisher(persistedEntity.getPublisher())
			.year(persistedEntity.getYear())
			.price(persistedEntity.getPrice())
			.synopsis(persistedEntity.getSynopsis())
			.content(persistedEntity.getContent())
			.genres(persistedEntity.getGenres())
			.build();
	}

	@Override
	public Page<Book> filter(GetBook.BookFilter filter) {
		List<Book> books = new ArrayList<>(map.values());
		if (filter.title() != null) books = filterTitle(books, filter.title());
		if (filter.author() != null) books = filterAuthorName(books, filter.author());
		if (filter.genre() != null) books = filterGenre(books, filter.genre());
		if (filter.from() != null) books = filterLowerBoundYear(books, filter.from());
		if (filter.to() != null) books = filterUpperBoundYear(books, filter.to());

		assert filter.ascendingSortByRating() != null &&
		filter.pageSize() != null &&
		filter.pageNumber() != null : "we relay on standard filter field that should be pass from application layer";

		books = sortByRating(books, filter.ascendingSortByRating());

		return new Page<>(books, filter.pageNumber(), filter.pageSize());
	}

	@Override
	public Result<Page<Review>> filterReview(String title, GetBookReviews.ReviewFilter filter) {
		Result<Book> bookResult = get(title);
		if (bookResult.isFailure())
			return Result.failure(bookResult.exception());

		Page<Review> page = new Page<>(bookResult.data().getReviewsList(), filter.pageNumber(), filter.pageSize());
		return Result.success(page);
	}

	private static List<Book> filterTitle(List<Book> books, String title) {
		return books.stream().filter(book -> book.getTitle().contains(title)).toList();
	}

	private static List<Book> filterAuthorName(List<Book> books, String name) {
		return books.stream().filter(book -> book.getAuthor().getName().contains(name)).toList();
	}

	private static List<Book> filterGenre(List<Book> books, String genre) {
		return books.stream().filter(book -> book.getGenres().contains(genre)).toList();
	}

	private static List<Book> filterLowerBoundYear(List<Book> books, int lowerBound) {
		return books.stream().filter(book -> book.getYear() >= lowerBound).toList();
	}

	private static List<Book> filterUpperBoundYear(List<Book> books, int upperBound) {
		return books.stream().filter(book -> book.getYear() <= upperBound).toList();
	}

	private static List<Book> sortByRating(List<Book> books, boolean isAscending) {
		return books
			.stream()
			.sorted(isAscending ? Comparator.comparing(Book::getAverageRating) : Comparator.comparing(Book::getAverageRating).reversed())
			.toList();
	}
}
