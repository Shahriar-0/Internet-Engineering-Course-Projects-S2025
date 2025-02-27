package infra.repositories;

import application.repositories.IBookRepository;
import domain.entities.Book;

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

}
