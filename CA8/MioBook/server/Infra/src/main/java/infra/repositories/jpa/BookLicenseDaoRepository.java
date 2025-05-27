package infra.repositories.jpa;

import infra.daos.BookLicenseDao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookLicenseDaoRepository extends JpaRepository<BookLicenseDao, Long> {
}
