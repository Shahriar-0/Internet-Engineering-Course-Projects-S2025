package infra.mappers;

import domain.entities.author.Author;
import domain.entities.book.Book;
import infra.daos.AuthorDao;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorMapper implements IMapper<Author, AuthorDao> {

    private final AdminMapper adminMapper;

    public Author mapWithBooks(AuthorDao dao, BookMapper bookMapper) {
        Author author = toDomain(dao);
        List<Book> books = dao.getBooksWritten().stream().map(bookMapper::toDomain).toList();
        author.setBooks(books);
        return author;
    }

    public List<Author> mapWithBooks(List<AuthorDao> daoList, BookMapper bookMapper) {
        return daoList.stream().map(dao -> mapWithBooks(dao, bookMapper)).toList();
    }

    @Override
    public Author toDomain(AuthorDao dao) {
        return Author.builder()
            .id(dao.getId())
            .name(dao.getName())
            .penName(dao.getPenName())
            .nationality(dao.getNationality())
            .born(dao.getBirthDate())
            .died(dao.getDeathDate())
            .admin(adminMapper.toDomain(dao.getAdmin()))
            .build();
    }

    @Override
    public AuthorDao toDao(Author entity) {
        AuthorDao dao = new AuthorDao();
        dao.setId(entity.getId());
        dao.setName(entity.getName());
        dao.setPenName(entity.getPenName());
        dao.setNationality(entity.getNationality());
        dao.setBirthDate(entity.getBorn());
        dao.setDeathDate(entity.getDied());
        dao.setAdmin(adminMapper.toDao(entity.getAdmin()));
        return dao;
    }

    @Override
    public void update(Author entity, AuthorDao dao) {
        dao.setId(entity.getId());
        dao.setName(entity.getName());
        dao.setPenName(entity.getPenName());
        dao.setNationality(entity.getNationality());
        dao.setBirthDate(entity.getBorn());
        dao.setDeathDate(entity.getDied());
        dao.setAdmin(adminMapper.toDao(entity.getAdmin()));
    }
}
