package infra.mappers;

import domain.entities.booklicense.BookLicense;
import domain.entities.booklicense.PermanentBookLicense;
import domain.entities.booklicense.TemporaryBookLicense;
import infra.daos.BookLicenseDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookLicenseMapper implements IMapper<BookLicense, BookLicenseDao> {

    private final BookMapper bookMapper;
    private final CustomerMapper customerMapper;

    @Override
    public BookLicense toDomain(BookLicenseDao dao) {
        if (dao.getValidityDays() == null) {
            return PermanentBookLicense.builder()
                .id(dao.getId())
                .price(dao.getPrice())
                .purchaseDate(dao.getPurchaseDateTime())
                .book(bookMapper.toDomain(dao.getBook()))
                .build();
        }
        else {
            return TemporaryBookLicense.builder()
                .id(dao.getId())
                .price(dao.getPrice())
                .purchaseDate(dao.getPurchaseDateTime())
                .book(bookMapper.toDomain(dao.getBook()))
                .validityDays(dao.getValidityDays())
                .build();
        }
    }

    @Override
    public BookLicenseDao toDao(BookLicense entity) {
        BookLicenseDao dao = new BookLicenseDao();
        dao.setId(entity.getId());
        dao.setPrice(entity.getPrice());
        dao.setPurchaseDateTime(entity.getPurchaseDate());
        dao.setCustomer(customerMapper.toDao(entity.getCustomer()));
        dao.setBook(bookMapper.toDao(entity.getBook()));
        if (entity instanceof PermanentBookLicense)
            dao.setValidityDays(null);
        else if (entity instanceof TemporaryBookLicense temporaryBookLicense)
            dao.setValidityDays(temporaryBookLicense.getValidityDays());
        else
            throw new IllegalArgumentException("Unknown BookLicense type!");

        return dao;
    }

    @Override
    public void update(BookLicense entity, BookLicenseDao dao) {
        dao.setId(entity.getId());
        dao.setPrice(entity.getPrice());
        dao.setPurchaseDateTime(entity.getPurchaseDate());
        dao.setCustomer(customerMapper.toDao(entity.getCustomer()));
        dao.setBook(bookMapper.toDao(entity.getBook()));
        if (entity instanceof PermanentBookLicense)
            dao.setValidityDays(null);
        else if (entity instanceof TemporaryBookLicense temporaryBookLicense)
            dao.setValidityDays(temporaryBookLicense.getValidityDays());
        else
            throw new IllegalArgumentException("Unknown BookLicense type!");
    }
}
