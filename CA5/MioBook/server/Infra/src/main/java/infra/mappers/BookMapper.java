package infra.mappers;

import domain.entities.book.Book;
import domain.entities.book.BookContent;
import infra.daos.BookDao;
import infra.daos.GenreDao;
import infra.repositories.jpa.GenreDaoRepository;
import infra.repositories.jpa.ReviewDaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookMapper implements IMapper<Book, BookDao> {

    private final GenreDaoRepository genreDaoRepository;
    private final ReviewDaoRepository reviewDaoRepository;
    private final AdminMapper adminMapper;
    private final AuthorMapper authorMapper;

    public Book mapWithAuthor(BookDao dao) {
        Book book = toDomain(dao);
        book.setAuthor(authorMapper.toDomain(dao.getAuthor()));
        return book;
    }

    public Book mapWithAuthorAndReviews(BookDao dao, ReviewMapper reviewMapper) {
        Book book = toDomain(dao);
        book.setAuthor(authorMapper.toDomain(dao.getAuthor()));
        book.setReviews(reviewDaoRepository.findByBookId(dao.getId()).stream().map(reviewMapper::toDomain).toList());
        return book;
    }

    @Override
    public Book toDomain(BookDao dao) {
        Book book = Book.builder()
            .id(dao.getId())
            .title(dao.getTitle())
            .publisher(dao.getPublisher())
            .publishedYear(dao.getPublishedYear())
            .basePrice(dao.getPrice())
            .synopsis(dao.getSynopsis())
            .genres(dao.getGenres().stream().map(GenreDao::getGenre).toList())
            .imageLink(dao.getImageLink())
            .coverLink(dao.getCoverLink())
            .dateAdded(dao.getDateAdded())
            .admin(adminMapper.toDomain(dao.getAdmin()))
            .author(authorMapper.toDomain(dao.getAuthor()))
            .build();

        book.setContent(BookContent.builder()
            .book(book)
            .content(dao.getContent())
            .build()
        );

        return book;
    }

    @Override
    public BookDao toDao(Book entity) {
        BookDao dao = new BookDao();
        dao.setId(entity.getId());
        dao.setTitle(entity.getTitle());
        dao.setPublisher(entity.getPublisher());
        dao.setPublishedYear(entity.getPublishedYear());
        dao.setPrice(entity.getBasePrice());
        dao.setSynopsis(entity.getSynopsis());
        dao.setContent(entity.getContent().getContent());
        dao.setImageLink(entity.getImageLink());
        dao.setCoverLink(entity.getCoverLink());
        dao.setDateAdded(entity.getDateAdded());
        dao.setGenres(getGenreDaoList(entity.getGenres()));
        dao.setAdmin(adminMapper.toDao(entity.getAdmin()));
        dao.setAuthor(authorMapper.toDao(entity.getAuthor()));
        return dao;
    }

    @Override
    public void update(Book entity, BookDao dao) {
        dao.setId(entity.getId());
        dao.setTitle(entity.getTitle());
        dao.setPublisher(entity.getPublisher());
        dao.setPublishedYear(entity.getPublishedYear());
        dao.setPrice(entity.getBasePrice());
        dao.setSynopsis(entity.getSynopsis());
        dao.setContent(entity.getContent().getContent());
        dao.setImageLink(entity.getImageLink());
        dao.setCoverLink(entity.getCoverLink());
        dao.setDateAdded(entity.getDateAdded());
        dao.setGenres(getGenreDaoList(entity.getGenres()));
        dao.setAdmin(adminMapper.toDao(entity.getAdmin()));
        dao.setAuthor(authorMapper.toDao(entity.getAuthor()));
    }

    private List<GenreDao> getGenreDaoList(List<String> genres) {
        List<GenreDao> daoList = new ArrayList<>();
        for (String genre : genres) {
            Optional<GenreDao> optionalDao = genreDaoRepository.findByGenre(genre);
            if (optionalDao.isEmpty()) {
                GenreDao dao = new GenreDao();
                dao.setGenre(genre);
                daoList.add(genreDaoRepository.save(dao));
            }
            else {
                daoList.add(optionalDao.get());
            }
        }

        return daoList;
    }
}
