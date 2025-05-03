package infra.repositories.jpa;

import infra.daos.CustomerDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerDaoRepository extends JpaRepository<CustomerDao, Long> {
    Optional<CustomerDao> findByName(String name);
}
