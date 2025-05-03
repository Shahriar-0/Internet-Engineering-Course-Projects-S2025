package infra.repositories.jpa;

import infra.daos.AdminDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminDaoRepository extends JpaRepository<AdminDao, Long> {
    Optional<AdminDao> findByName(String name);
}
