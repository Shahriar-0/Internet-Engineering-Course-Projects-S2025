package infra.repositories.application;

import application.repositories.IAuthorRepository;
import domain.entities.author.Author;
import infra.daos.AuthorDao;
import infra.mappers.AuthorMapper;
import infra.mappers.BookMapper;
import infra.mappers.IMapper;
import infra.repositories.jpa.AuthorDaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class AuthorRepository extends BaseRepository<Author, AuthorDao> implements IAuthorRepository {

    private final AuthorDaoRepository authorDaoRepository;
    private final AuthorMapper authorMapper;
    private final BookMapper bookMapper;

    @Override
    protected IMapper<Author, AuthorDao> getMapper() {
        return authorMapper;
    }

    @Override
    protected JpaRepository<AuthorDao, Long> getDaoRepository() {
        return authorDaoRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Author> findByName(String name) {
        Optional<AuthorDao> optionalDao = authorDaoRepository.findByName(name);
        if (optionalDao.isEmpty())
            return Optional.empty();

        Author author = authorMapper.mapWithBooks(optionalDao.get(), bookMapper);
        return Optional.of(author);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Author> findAllWithBooks() {
        return authorMapper.mapWithBooks(
            authorDaoRepository.findAll(),
            bookMapper
        );
    }
}
