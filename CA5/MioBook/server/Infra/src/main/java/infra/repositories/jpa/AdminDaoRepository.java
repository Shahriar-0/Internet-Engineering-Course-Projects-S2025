package infra.repositories.jpa;

import infra.daos.AdminDao;
import infra.daos.CustomerDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminDaoRepository extends JpaRepository<AdminDao, Long> {
    Optional<AdminDao> findByName(String name);
    Optional<AdminDao> findByEmail(String email);
    boolean existsByName(String name);
    boolean existsByEmail(String email);
}
