package infra.repositories.application;

import application.repositories.IAuthorRepository;
import domain.entities.author.Author;
import infra.daos.AuthorDao;
import infra.mappers.AuthorMapper;
import infra.mappers.IMapper;
import infra.repositories.jpa.AuthorDaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorRepository extends BaseRepository<Author, AuthorDao> implements IAuthorRepository {

    private final AuthorDaoRepository authorDaoRepository;
    private final AuthorMapper authorMapper;

    @Override
    protected IMapper<Author, AuthorDao> getMapper() {
        return authorMapper;
    }

    @Override
    protected JpaRepository<AuthorDao, Long> getDaoRepository() {
        return authorDaoRepository;
    }

    @Override
    public Optional<Author> findByName(String name) {
        Optional<AuthorDao> optionalDao = authorDaoRepository.findByName(name);
        if (optionalDao.isEmpty())
            return Optional.empty();

        Author author = authorMapper.toDomain(optionalDao.get());
        return Optional.of(author);
    }
}
