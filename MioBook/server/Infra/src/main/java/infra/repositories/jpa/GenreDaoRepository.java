package infra.repositories.jpa;

import infra.daos.GenreDao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GenreDaoRepository extends JpaRepository<GenreDao, Long> {
    Optional<GenreDao> findByGenre(String genre);
}
