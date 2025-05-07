package infra.repositories.jpa;

import infra.daos.AuthorDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorDaoRepository extends JpaRepository<AuthorDao, Long> {
    Optional<AuthorDao> findByName(String name);
}
