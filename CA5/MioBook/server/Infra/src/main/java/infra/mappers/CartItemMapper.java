package infra.mappers;

import org.springframework.stereotype.Component;

import domain.entities.cart.CartItem;
import infra.daos.CartItemDao;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CartItemMapper implements IMapper<CartItem, CartItemDao> {

    private final BookMapper bookMapper;
    private final CustomerMapper customerMapper;

    @Override
    public CartItem toDomain(CartItemDao dao) {
        return CartItem.builder()
            .id(dao.getId())
            .book(bookMapper.toDomain(dao.getBook()))
            .isBorrowed(dao.isBorrowed())
            .borrowDays(dao.getBorrowDays())
            .build();
    }

    @Override
    public CartItemDao toDao(CartItem entity) {
        CartItemDao dao = new CartItemDao();
        dao.setId(entity.getId());
        dao.setBorrowed(entity.isBorrowed());
        dao.setBorrowDays(entity.getBorrowDays());
        dao.setBook(bookMapper.toDao(entity.getBook()));
        dao.setCustomer(customerMapper.toDao(entity.getCart().getCustomer()));
        return dao;
    }

    @Override
    public void update(CartItem entity, CartItemDao dao) {
        dao.setId(entity.getId());
        dao.setBorrowed(entity.isBorrowed());
        dao.setBorrowDays(entity.getBorrowDays());
        dao.setBook(bookMapper.toDao(entity.getBook()));
        dao.setCustomer(customerMapper.toDao(entity.getCart().getCustomer()));
    }
}
