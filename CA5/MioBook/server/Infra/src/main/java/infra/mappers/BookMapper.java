package infra.mappers;

import domain.entities.book.Book;
import domain.entities.book.BookContent;
import domain.entities.book.Review;
import infra.daos.BookDao;
import infra.daos.GenreDao;
import infra.repositories.jpa.GenreDaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BookMapper implements IMapper<Book, BookDao> {

    private final GenreDaoRepository genreDaoRepository;

    public Book mapWithAuthor(BookDao dao, AuthorMapper authorMapper) {
        Book book = toDomain(dao);
        book.setAuthor(authorMapper.toDomain(dao.getAuthor()));
        return book;
    }

    public Book mapWithAuthorAndReviews(BookDao dao, AuthorMapper authorMapper, ReviewMapper reviewMapper) {
        Book book = mapWithAuthor(dao, authorMapper);
        List<Review> reviews = dao.getReviews().stream().map(reviewMapper::toDomain).toList();
        book.setReviews(reviews);
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
