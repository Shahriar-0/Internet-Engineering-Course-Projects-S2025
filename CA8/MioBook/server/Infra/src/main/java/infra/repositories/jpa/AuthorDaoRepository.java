package infra.repositories.jpa;

import infra.daos.AuthorDao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorDaoRepository extends JpaRepository<AuthorDao, Long> {
	Optional<AuthorDao> findByName(String name);

	@Query("SELECT a FROM AuthorDao a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :name, '%'))")
	List<AuthorDao> searchByName(@Param("name") String name);
}
