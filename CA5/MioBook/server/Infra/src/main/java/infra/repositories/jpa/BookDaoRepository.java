package infra.repositories.jpa;

import infra.daos.BookDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookDaoRepository extends JpaRepository<BookDao, Long> {
    Optional<BookDao> findByTitle(String title);
}
