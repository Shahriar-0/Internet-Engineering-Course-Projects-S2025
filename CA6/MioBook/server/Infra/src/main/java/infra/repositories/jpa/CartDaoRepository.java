package infra.repositories.jpa;

import infra.daos.CartItemDao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDaoRepository extends JpaRepository<CartItemDao, Long> {
    List<CartItemDao> findByCustomerId(Long customerId);
    void deleteByCustomerId(Long customerId);
    void deleteByCustomerIdAndBookId(Long customerId, Long bookId);
    Optional<CartItemDao> findByCustomerIdAndBookId(Long customerId, Long bookId);
}
