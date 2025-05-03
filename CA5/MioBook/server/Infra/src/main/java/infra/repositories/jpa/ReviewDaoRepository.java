package infra.repositories.jpa;

import infra.daos.ReviewDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewDaoRepository extends JpaRepository<ReviewDao, Long>, JpaSpecificationExecutor<ReviewDao> {
}
