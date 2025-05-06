package infra.repositories.application;

import application.repositories.IBookRepository;
import application.usecase.user.book.GetBook;
import application.usecase.user.book.GetBookReviews;
import domain.entities.book.Book;
import domain.entities.book.Review;
import infra.daos.BookDao;
import infra.daos.GenreDao;
import infra.daos.ReviewDao;
import infra.mappers.*;
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

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class BookRepository extends BaseRepository<Book, BookDao> implements IBookRepository {

    private final BookDaoRepository bookDaoRepository;
    private final GenreDaoRepository genreDaoRepository;
    private final ReviewDaoRepository reviewDaoRepository;
    private final BookMapper bookMapper;
    private final AuthorMapper authorMapper;
    private final ReviewMapper reviewMapper;
    private final CustomerMapper customerMapper;

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
        Sort sort = Sort.unsorted();
        if (filter.sortBy() != null) {
            sort = getSortObject(filter.sortByType(), filter.isAscending());
        }

        Pageable pageable = PageRequest.of(
            filter.pageNumber() - 1,
            filter.pageSize(),
            sort
        );

        Specification<BookDao> spec = Specification.where(null);

        if (filter.title() != null)
            spec = spec.and((root, query, cb) ->
                cb.like(cb.lower(root.get("title")), "%" + filter.title().toLowerCase() + "%")
            );

        if (filter.author() != null)
            spec = spec.and((root, query, cb) ->
                cb.like(cb.lower(root.get("author")), "%" + filter.author().toLowerCase() + "%")
            );

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

        return bookDaoRepository.findAll(spec, pageable).map(dao -> bookMapper.mapWithAuthor(dao, authorMapper));
    }

    private static Sort getSortObject(GetBook.BookFilter.BookSortByType sortByType, Boolean isAscending) {
        String propertyName = switch (sortByType) {
            case DATE -> "date_added";
            // case RATING -> "averageRating";
            // case REVIEWS -> "reviewsCount"; // TODO: implement, it should search among review table and find the shits
            case TITLE -> "title";
            default -> "title"; // TODO: temp solution just to fix switch
        };

        return isAscending ? Sort.by(propertyName).ascending() : Sort.by(propertyName).descending();
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
            .map(dao -> reviewMapper.mapWithCustomer(dao, customerMapper));
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

        Book book = bookMapper.mapWithAuthorAndReviews(optionalDao.get(), authorMapper, reviewMapper);
        return Optional.of(book);
    }
}
