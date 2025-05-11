package infra.repositories.jpa;

import infra.daos.PurchasedCartDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchasedCartDaoRepository extends JpaRepository<PurchasedCartDao, Long> {
}
