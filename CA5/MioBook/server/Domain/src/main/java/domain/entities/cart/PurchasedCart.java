package domain.entities.cart;

import domain.entities.DomainEntity;
import domain.entities.user.Customer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class PurchasedCart extends DomainEntity {

	private final Customer customer;
	private List<PurchasedItem> items = new ArrayList<>(); // FIXME: make this final
	private final LocalDateTime purchaseDateTime;

	public PurchasedCart(Cart cart, LocalDateTime purchaseDate) {
		this.customer = cart.getCustomer();
		this.purchaseDateTime = purchaseDate;
		cart.getItems().forEach(item -> items.add(new PurchasedItem(item, this)));
	}

	public long getTotalCost() {
		return items.stream().map(PurchasedItem::getPrice).reduce(0L, Long::sum);
	}
}
