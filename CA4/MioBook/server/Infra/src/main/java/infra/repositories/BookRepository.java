package infra.repositories;

import application.pagination.Page;
import application.repositories.IBookRepository;
import application.result.Result;
import application.usecase.user.book.GetBook;
import application.usecase.user.book.GetBookReviews;
import domain.entities.book.Book;
import domain.entities.book.Review;
import java.util.*;

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
			.publishedYear(persistedEntity.getPublishedYear())
			.basePrice(persistedEntity.getBasePrice())
			.synopsis(persistedEntity.getSynopsis())
			.content(persistedEntity.getContent())
			.genres(persistedEntity.getGenres())
			.reviews(persistedEntity.getReviews())
			.dateAdded(persistedEntity.getDateAdded())
			.coverLink(persistedEntity.getCoverLink())
			.build();
	}

	@Override
	public Page<Book> filter(GetBook.BookFilter filter) {
		List<Book> books = new ArrayList<>(map.values());
		if (filter.title() != null)
			books = filterTitle(books, filter.title());
		if (filter.author() != null)
			books = filterAuthorName(books, filter.author());
		if (filter.genre() != null)
			books = filterGenre(books, filter.genre());
		if (filter.from() != null)
			books = filterLowerBoundYear(books, filter.from());
		if (filter.to() != null)
			books = filterUpperBoundYear(books, filter.to());

		assert filter.pageSize() != null &&
		filter.pageNumber() != null &&
		filter.sortBy() != null : "we rely on standard filter field that should be pass from application layer";

		if (filter.sortBy() != null) {
			books = sortBooks(books, getSortFunction(filter.sortByType()), filter.isAscending());
		}

		return new Page<>(books, filter.pageNumber(), filter.pageSize());
	}

	private static Comparator<Book> getSortFunction(GetBook.BookFilter.BookSortByType filterType) {
		return switch (filterType) {
			case DATE -> Comparator.comparing(Book::getDateAdded);
			case RATING -> Comparator.comparing(Book::getAverageRating);
			case REVIEWS -> Comparator.comparing(book -> book.getReviews().size());
		};
	}

	private static List<Book> sortBooks(List<Book> books, Comparator<Book> comparator, boolean isAscending) {
		return books.stream().sorted(isAscending ? comparator : comparator.reversed()).toList();
	}

	@Override
	public Result<Page<Review>> filterReview(String title, GetBookReviews.ReviewFilter filter) {
		Result<Book> bookResult = get(title);
		if (bookResult.isFailure())
			return Result.failure(bookResult.exception());

		Page<Review> page = new Page<>(bookResult.data().getReviews(), filter.pageNumber(), filter.pageSize());
		return Result.success(page);
	}

	@Override
	public List<String> getGenres() {
		List<Book> books = new ArrayList<>(map.values());
		List<List<String>> genresList = books.stream().map(Book::getGenres).toList();
		Set<String> genresSet = new HashSet<>();
		for (List<String> genres : genresList)
			genresSet.addAll(genres);

		return genresSet.stream().toList();
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
		return books.stream().filter(book -> book.getPublishedYear() >= lowerBound).toList();
	}

	private static List<Book> filterUpperBoundYear(List<Book> books, int upperBound) {
		return books.stream().filter(book -> book.getPublishedYear() <= upperBound).toList();
	}
}
