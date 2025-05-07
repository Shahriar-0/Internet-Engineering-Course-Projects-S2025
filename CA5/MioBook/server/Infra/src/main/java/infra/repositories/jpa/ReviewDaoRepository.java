package infra.repositories.jpa;

import infra.daos.ReviewDao;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewDaoRepository extends JpaRepository<ReviewDao, Long>, JpaSpecificationExecutor<ReviewDao> {
    Optional<ReviewDao> findByBookIdAndCustomerId(Long bookId, Long customerId);
    void deleteByBookIdAndCustomerId(Long bookId, Long customerId);
    List<ReviewDao> findByBookId(Long bookId);
}
