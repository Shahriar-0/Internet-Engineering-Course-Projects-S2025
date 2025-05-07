package infra.repositories.application;

import application.repositories.IBookRepository;
import application.usecase.user.book.GetBook;
import application.usecase.user.book.GetBookReviews;
import domain.entities.book.Book;
import domain.entities.book.Review;
import domain.entities.user.Customer;
import infra.daos.AuthorDao;
import infra.daos.BookDao;
import infra.daos.GenreDao;
import infra.daos.ReviewDao;
import infra.mappers.*;
import infra.repositories.jpa.AuthorDaoRepository;
import infra.repositories.jpa.BookDaoRepository;
import infra.repositories.jpa.GenreDaoRepository;
import infra.repositories.jpa.ReviewDaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

@Repository
@RequiredArgsConstructor
public class BookRepository extends BaseRepository<Book, BookDao> implements IBookRepository {

    private final BookDaoRepository bookDaoRepository;
    private final GenreDaoRepository genreDaoRepository;
    private final ReviewDaoRepository reviewDaoRepository;
    private final AuthorDaoRepository authorDaoRepository;
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
    @Transactional(readOnly = true)
    public Page<Book> filter(GetBook.BookFilter filter) {

        Pageable pageable = PageRequest.of(
            filter.pageNumber() - 1,
            filter.pageSize()
        );

        Specification<BookDao> spec = Specification.where(null);

        if (filter.title() != null)
            spec = spec.and((root, query, cb) ->
                cb.like(cb.lower(root.get("title")), "%" + filter.title().toLowerCase() + "%")
            );

        if (filter.author() != null) {
            Optional<AuthorDao> authorDao = authorDaoRepository.findByName(filter.author());
            if (authorDao.isPresent()) {
                spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("author"), authorDao.get())
                );
            }
        }

        if (filter.genre() != null)
            spec = spec.and((root, query, cb) ->
                cb.equal(root.get("genre"), filter.genre())
            );

        if (filter.from() != null)
            spec = spec.and((root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("year"), filter.from())
            );

        if (filter.to() != null)
            spec = spec.and((root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("year"), filter.to())
            );

        Page<Book> page = bookDaoRepository
            .findAll(spec, pageable)
            .map(dao -> bookMapper.mapWithAuthorAndReviews(dao, reviewMapper));

        // sort content in‑memory
        List<Book> sortedList = new ArrayList<>(page.getContent());
        Comparator<Book> comparator = getSortFunction(filter.sortByType());
        sortedList = sortedList.stream().sorted(filter.isAscending() ? comparator : comparator.reversed()).toList();

        return new PageImpl<>( // TODO: improve this sorting
            sortedList,
            page.getPageable(),
            page.getTotalElements()
        );
    }

	private static Comparator<Book> getSortFunction(GetBook.BookFilter.BookSortByType filterType) {
		return switch (filterType) {
			case DATE -> Comparator.comparing(Book::getDateAdded);
			case RATING -> Comparator.comparing(Book::getAverageRating);
			case REVIEWS -> Comparator.comparing(book -> book.getReviews().size());
			case TITLE -> Comparator.comparing(Book::getTitle);
		};
	}

	@Override
    @Transactional(readOnly = true)
	public Page<Review> filterReview(String title, GetBookReviews.ReviewFilter filter) {
        Pageable pageable = PageRequest.of(
            filter.pageNumber() - 1,
            filter.pageSize(),
            Sort.by(Sort.Direction.DESC, "dateTime")
        );

        Specification<ReviewDao> spec = Specification.where(null);
        spec = spec.and((root, query, cb) ->
            cb.like(cb.lower(root.get("book").get("title")), "%" + title.toLowerCase() + "%"));

        return reviewDaoRepository.findAll(spec, pageable)
            .map(dao -> reviewMapper.mapWithCustomer(dao));
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

        Book book = bookMapper.mapWithAuthor(optionalDao.get());
        return Optional.of(book);
    }

    @Override
    @Transactional
    public Review upsertReview(Review review, Book book, Customer customer) {
        if (reviewDaoRepository.findByBookIdAndCustomerId(book.getId(), customer.getId()).isPresent())
            reviewDaoRepository.deleteByBookIdAndCustomerId(book.getId(), customer.getId());

        ReviewDao dao = reviewMapper.toDao(review);
        dao = reviewDaoRepository.save(dao);
        return reviewMapper.toDomain(dao);
    }
}
