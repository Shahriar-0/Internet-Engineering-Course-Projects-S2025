package infra.repositories;

import application.pagination.Page;
import application.repositories.IBookRepository;
import application.result.Result;
import application.usecase.user.GetBookReviewsUseCase;
import application.usecase.user.GetBookUseCase;
import domain.entities.Book;
import domain.valueobjects.BookSearch;
import domain.valueobjects.Review;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
	public Result<BookSearch> search(Map<String, String> params) {
		List<Book> books = new ArrayList<>(map.values());
		if (params.containsKey("title"))
			books = filterTitle(books, params.get("title"));
		if (params.containsKey("username"))
			books = filterAuthorName(books, params.get("username"));
		if (params.containsKey("genre"))
			books = filterGenre(books, params.get("genre"));
		if (params.containsKey("from") && params.containsKey("to")) {
			int from = Integer.parseInt(params.get("from"));
			int to = Integer.parseInt(params.get("to"));
			books = filterYear(books, from, to);
		}

		return Result.success(new BookSearch(books, params));
	}

	@Override
	public Page<Book> filter(GetBookUseCase.BookFilter filter) {
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
	public Page<Review> filter(Book book, GetBookReviewsUseCase.ReviewFilter filter) { // FIXME: didnt have better solution for this
		return new Page<>(book.getReviewsList(), filter.pageNumber(), filter.pageSize());
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

	private static List<Book> filterYear(List<Book> books, int from, int to) {
		return books.stream().filter(book -> book.getYear() >= from && book.getYear() <= to).toList();
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
