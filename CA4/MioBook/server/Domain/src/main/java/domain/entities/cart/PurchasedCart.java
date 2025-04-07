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
public class PurchasedCart extends DomainEntity<PurchasedCart.Key> {

	private final Customer customer;
	private final List<PurchasedCartItem> items = new ArrayList<>();
	private final LocalDateTime purchaseDate;
    private final long totalCost;

    public Long getId() {
		return key.id;
	}

    public String getCustomerName() {
        return key.customerName;
    }

    public PurchasedCart(Cart cart, long id) {
        super(new Key(cart.getCustomer().getUsername(), id));
        this.customer = cart.getCustomer();
        this.purchaseDate = LocalDateTime.now();
        this.totalCost = cart.getTotalCost();
        cart.getItems().forEach(item -> items.add(new PurchasedCartItem(item, id)));
    }

    public PurchasedCart(Cart cart, long id, LocalDateTime purchaseDate) {
        super(new Key(cart.getCustomer().getUsername(), id));
        this.customer = cart.getCustomer();
        this.purchaseDate = purchaseDate;
        this .totalCost = cart.getTotalCost();
        cart.getItems().forEach(item -> items.add(new PurchasedCartItem(item, id)));
    }

	public record Key(String customerName, long id) {}
}
