package infra.mappers;

import domain.entities.author.Author;
import infra.daos.AuthorDao;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper implements IMapper<Author, AuthorDao> {
    @Override
    public Author toDomain(AuthorDao dao) {
        return Author.builder()
            .id(dao.getId())
            .name(dao.getName())
            .penName(dao.getPenName())
            .nationality(dao.getNationality())
            .born(dao.getBirthDate())
            .died(dao.getDeathDate())
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
    }
}
