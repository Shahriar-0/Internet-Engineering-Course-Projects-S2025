package infra.repositories.application;

import application.repositories.IBookRepository;
import application.usecase.user.book.GetBook;
import application.usecase.user.book.GetBookReviews;
import domain.entities.book.Book;
import domain.entities.book.Review;
import infra.daos.BookDao;
import infra.daos.GenreDao;
import infra.daos.ReviewDao;
import infra.mappers.BookMapper;
import infra.mappers.IMapper;
import infra.mappers.ReviewMapper;
import infra.repositories.jpa.BookDaoRepository;
import infra.repositories.jpa.GenreDaoRepository;
import infra.repositories.jpa.ReviewDaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static com.fasterxml.classmate.AnnotationOverrides.builder;

@Repository
@RequiredArgsConstructor
public class BookRepository extends BaseRepository<Book, BookDao> implements IBookRepository {

    private final BookDaoRepository bookDaoRepository;
    private final GenreDaoRepository genreDaoRepository;
    private final ReviewDaoRepository reviewDaoRepository;
    private final BookMapper bookMapper;
    private final ReviewMapper reviewMapper;


    @Override
    protected IMapper<Book, BookDao> getMapper() {
        return bookMapper;
    }

    @Override
    protected JpaRepository<BookDao, Long> getDaoRepository() {
        return bookDaoRepository;
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
		filter.sortByType() != null &&
		filter.isAscending() != null : "we rely on standard filter field that should be pass from application layer";

		if (filter.sortBy() != null) {
			// since we are comparing with this field which may be null is request
			// the default value for TITLE when not passed is not considered
			books = sortBooks(books, getSortFunction(filter.sortByType()), filter.isAscending());
		}

		return new Page<>(books, filter.pageNumber(), filter.pageSize());
	}

	private static Comparator<Book> getSortFunction(GetBook.BookFilter.BookSortByType filterType) {
		return switch (filterType) {
			case DATE -> Comparator.comparing(Book::getDateAdded);
			case RATING -> Comparator.comparing(Book::getAverageRating);
			case REVIEWS -> Comparator.comparing(book -> book.getReviews().size());
			case TITLE -> Comparator.comparing(Book::getTitle);
		};
	}

	private static List<Book> sortBooks(List<Book> books, Comparator<Book> comparator, boolean isAscending) {
		return books.stream().sorted(isAscending ? comparator : comparator.reversed()).toList();
	}

	@Override
    @Transactional(readOnly = true)
	public Page<Review> filterReview(String title, GetBookReviews.ReviewFilter filter) {
        Pageable pageable = PageRequest.of(
            filter.pageNumber(),
            filter.pageSize(),
            Sort.by(Sort.Direction.DESC, "dateTime")
        );

        Specification<ReviewDao> spec = Specification.where(null);
        spec = spec.and((root, query, cb) ->
            cb.like(cb.lower(root.get("book").get("title")), "%" + title.toLowerCase() + "%"));

        return reviewDaoRepository.findAll(spec, pageable).map(reviewMapper::toDomain);
	}

	@Override
    @Transactional(readOnly = true)
	public List<String> getGenres() {
		return genreDaoRepository.findAll().stream().map(GenreDao::getGenre).toList();
	}

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findByTitle(String title) {
        Optional<BookDao> optionalDao = bookDaoRepository.findByTitle(title);
        if (optionalDao.isEmpty())
            return Optional.empty();

        Book book = bookMapper.toDomain(optionalDao.get());
        return Optional.of(book);
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
