package infra.mappers;

import org.springframework.stereotype.Component;

import domain.entities.cart.PurchasedCart;
import infra.daos.PurchasedCartDao;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PurchasedCartMapper implements IMapper<PurchasedCart, PurchasedCartDao> {

    private final CustomerMapper customerMapper;

	public PurchasedCart mapWithItems(PurchasedCartDao dao, PurchasedItemMapper itemMapper) {
		PurchasedCart cart = toDomain(dao);
		cart.setItems(dao.getPurchasedItems().stream().map(itemMapper::toDomain).toList());
		return cart;
	}

	@Override
	public PurchasedCart toDomain(PurchasedCartDao dao) {
		return PurchasedCart.builder()
            .id(dao.getId())
            .purchaseDateTime(dao.getPurchaseDateTime())
            .customer(customerMapper.toDomain(dao.getCustomer()))
            .build();
	}

	@Override
	public PurchasedCartDao toDao(PurchasedCart entity) {
		PurchasedCartDao dao = new PurchasedCartDao();
		dao.setId(entity.getId());
		dao.setPurchaseDateTime(entity.getPurchaseDateTime());
		dao.setCustomer(customerMapper.toDao(entity.getCustomer()));
		return dao;
	}

	@Override
	public void update(PurchasedCart entity, PurchasedCartDao dao) {
		dao.setId(entity.getId());
		dao.setPurchaseDateTime(entity.getPurchaseDateTime());
		dao.setCustomer(customerMapper.toDao(entity.getCustomer()));
	}
}
