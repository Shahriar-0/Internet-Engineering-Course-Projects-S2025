package infra.mappers;

import org.springframework.stereotype.Component;

import domain.entities.cart.PurchasedItem;
import infra.daos.PurchasedItemDao;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PurchasedItemMapper implements IMapper<PurchasedItem, PurchasedItemDao> {

    private final BookMapper bookMapper;
    private final PurchasedCartMapper purchasedCartMapper;

    @Override
    public PurchasedItem toDomain(PurchasedItemDao dao) {
        return PurchasedItem.builder()
                .id(dao.getId())
                .isBorrowed(dao.isBorrowed())
                .borrowDays(dao.getBorrowDays())
                .price(dao.getPrice())
                .book(bookMapper.toDomain(dao.getBook()))
                .cart(purchasedCartMapper.toDomain(dao.getPurchasedCart()))
                .build();
    }

    @Override
    public PurchasedItemDao toDao(PurchasedItem entity) {
        PurchasedItemDao dao = new PurchasedItemDao();
        dao.setId(entity.getId());
        dao.setBorrowed(entity.isBorrowed());
        dao.setBorrowDays(entity.getBorrowDays());
        dao.setPrice(entity.getPrice());
        dao.setBook(bookMapper.toDao(entity.getBook()));
        dao.setPurchasedCart(purchasedCartMapper.toDao(entity.getCart()));
        return dao;
    }

    @Override
    public void update(PurchasedItem entity, PurchasedItemDao dao) {
        dao.setId(entity.getId());
        dao.setBorrowed(entity.isBorrowed());
        dao.setBorrowDays(entity.getBorrowDays());
        dao.setPrice(entity.getPrice());
        dao.setBook(bookMapper.toDao(entity.getBook()));
        dao.setPurchasedCart(purchasedCartMapper.toDao(entity.getCart()));
    }

}
