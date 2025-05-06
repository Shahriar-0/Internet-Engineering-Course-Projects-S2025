package domain.entities.cart;

import domain.entities.DomainEntity;
import domain.entities.user.Customer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class PurchasedCart extends DomainEntity {

	private final Customer customer;
	private final List<PurchasedItem> items = new ArrayList<>();
	private final LocalDateTime purchaseDate;

	public PurchasedCart(Cart cart, LocalDateTime purchaseDate) {
		this.customer = cart.getCustomer();
		this.purchaseDate = purchaseDate;
		cart.getItems().forEach(item -> items.add(new PurchasedItem(item)));
	}

	public long getTotalCost() {
		return items.stream().map(PurchasedItem::getPrice).reduce(0L, Long::sum);
	}
}
