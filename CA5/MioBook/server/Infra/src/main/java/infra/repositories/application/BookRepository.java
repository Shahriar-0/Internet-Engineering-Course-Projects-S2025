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

import java.util.List;
import java.util.Optional;

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
    @Transactional(readOnly = true)
	public Page<Book> filter(GetBook.BookFilter filter) {
        //TODO: implement this fucking peace of shit, i passed out
        return Page.empty();
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

        return reviewDaoRepository.findAll(spec, pageable).map(reviewMapper::mapWithCustomer);
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
}
