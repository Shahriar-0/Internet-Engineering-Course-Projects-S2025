package infra.repositories.application;

import application.repositories.IAuthorRepository;
import application.usecase.user.author.GetAuthor;
import domain.entities.author.Author;
import infra.daos.AdminDao;
import infra.daos.AuthorDao;
import infra.mappers.AuthorMapper;
import infra.mappers.BookMapper;
import infra.mappers.IMapper;
import infra.repositories.jpa.AdminDaoRepository;
import infra.repositories.jpa.AuthorDaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AuthorRepository extends BaseRepository<Author, AuthorDao> implements IAuthorRepository {

    private final AuthorDaoRepository authorDaoRepository;
    private final AdminDaoRepository adminDaoRepository;
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

    @Override
    @Transactional(readOnly = true)
    public Page<Author> filter(GetAuthor.AuthorFilter filter) {
        Pageable pageable = PageRequest.of(
            filter.pageNumber() - 1,
            filter.pageSize()
        );

        Specification<AuthorDao> spec = Specification.where(null);

        if (filter.name() != null && !filter.name().isBlank()) {
            spec = spec.and((root, query, cb) ->
                cb.like(cb.lower(root.get("name")), "%" + filter.name().toLowerCase() + "%")
            );
        }
        if (filter.nationality() != null && !filter.nationality().isBlank()) {
            spec = spec.and((root, query, cb) ->
                cb.equal(cb.lower(root.get("nationality")), filter.nationality().toLowerCase())
            );
        }

        if (filter.admin() != null) {
            Optional<AdminDao> adminDao = adminDaoRepository.findByName(filter.admin());
            if (adminDao.isEmpty())
                return Page.empty(pageable);

                spec = spec.and((root, query, cb) ->
                cb.equal(root.get("admin"), adminDao.get())
            );
        }

        Page<AuthorDao> page = authorDaoRepository.findAll(spec, pageable);
        List<Author> authors = page.getContent().stream()
            .map(dao -> authorMapper.mapWithBooks(dao, bookMapper))
            .collect(Collectors.toList());
        return new PageImpl<>(authors, pageable, page.getTotalElements());
    }
}
