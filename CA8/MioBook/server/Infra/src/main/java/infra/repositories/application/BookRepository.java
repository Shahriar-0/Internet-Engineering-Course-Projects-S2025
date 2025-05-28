package infra.repositories.application;

import application.repositories.IBookRepository;
import application.usecase.user.book.GetBook;
import application.usecase.user.book.GetBookReviews;
import domain.entities.book.Book;
import domain.entities.book.Review;
import domain.entities.user.Customer;
import infra.daos.AdminDao;
import infra.daos.AuthorDao;
import infra.daos.BookDao;
import infra.daos.GenreDao;
import infra.daos.ReviewDao;
import infra.mappers.BookMapper;
import infra.mappers.IMapper;
import infra.mappers.ReviewMapper;
import infra.repositories.jpa.AdminDaoRepository;
import infra.repositories.jpa.AuthorDaoRepository;
import infra.repositories.jpa.BookDaoRepository;
import infra.repositories.jpa.GenreDaoRepository;
import infra.repositories.jpa.ReviewDaoRepository;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
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

import static application.usecase.user.book.GetBook.BookFilter.BookSortByType.*;

@Repository
@RequiredArgsConstructor
public class BookRepository extends BaseRepository<Book, BookDao> implements IBookRepository {

    private final BookDaoRepository bookDaoRepository;
    private final GenreDaoRepository genreDaoRepository;
    private final ReviewDaoRepository reviewDaoRepository;
    private final AuthorDaoRepository authorDaoRepository;
    private final AdminDaoRepository adminDaoRepository;
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
            if (authorDao.isPresent()) { // FIXME: for when there is no author we should return empty list
                spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("author"), authorDao.get())
                );
            }
        }

        if (filter.admin() != null) {
            Optional<AdminDao> adminDao = adminDaoRepository.findByName(filter.admin());
            if (adminDao.isPresent()) { // FIXME: for when there is no admin we should return empty list
                spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("admin"), adminDao.get())
                );
            }
        }

        if (filter.genre() != null) {
            Optional<GenreDao> genreDao = genreDaoRepository.findByGenre(filter.genre());
            if (genreDao.isPresent()) { // FIXME: for when there is no genre we should return empty list
                spec = spec.and((root, query, cb) ->
                    cb.isMember(genreDao.get(), root.get("genres"))
                );
            }
        }

        if (filter.from() != null)
            spec = spec.and((root, query, cb) ->
                cb.greaterThanOrEqualTo(root.get("publishedYear"), filter.from())
            );

        if (filter.to() != null)
            spec = spec.and((root, query, cb) ->
                cb.lessThanOrEqualTo(root.get("publishedYear"), filter.to())
            );

        spec = addSortToSpec(spec, filter.sortByType(), filter.isAscending());

        return bookDaoRepository
            .findAll(spec, pageable)
            .map(dao -> bookMapper.mapWithAuthorAndReviews(dao, reviewMapper));
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

    @Override
    @Transactional(readOnly = true)
    public boolean existsByTitle(String title) {
        return bookDaoRepository.existsByTitle(title);
    }

    private static Specification<BookDao> addSortToSpec(Specification<BookDao> spec, GetBook.BookFilter.BookSortByType sortType, boolean isAscending) {
        if (sortType == RATING || sortType == REVIEWS)
            spec = spec.and((root, query, cb) -> {
                Join<BookDao, ReviewDao> ratings = root.join("reviews", JoinType.LEFT);
                query.groupBy(root.get("id"));
                if (sortType == RATING) {
                    Expression<Double> avgRating = cb.avg(ratings.get("rating"));
                    query.orderBy(isAscending ? cb.asc(avgRating) : cb.desc(avgRating));
                }
                else {
                    Expression<Long> ratingCount = cb.count(ratings);
                    query.orderBy(isAscending ? cb.asc(ratingCount) : cb.desc(ratingCount));
                }
                return cb.conjunction();
            });

        if (sortType == DATE)
            spec = spec.and((root, query, cb) -> {
                query.orderBy(isAscending ? cb.asc(root.get("publishedYear")) : cb.desc(root.get("publishedYear")));
                return cb.conjunction();
            });

        if (sortType == TITLE)
            spec = spec.and((root, query, cb) -> {
                query.orderBy(isAscending ? cb.asc(root.get("title")) : cb.desc(root.get("title")));
                return cb.conjunction();
            });

        return spec;
    }
}
