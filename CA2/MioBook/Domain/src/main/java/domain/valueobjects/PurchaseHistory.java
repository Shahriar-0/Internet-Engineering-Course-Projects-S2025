package domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import domain.entities.Customer;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public class PurchaseHistory {

	@JsonIgnore
	private final Customer customer;

	@JsonIgnore
	private final List<PurchasedCart> purchasedCarts;

	public PurchaseHistory(Customer customer) {
		this.customer = customer;
		this.purchasedCarts = new ArrayList<>();
	}

	public PurchasedCart addPurchasedCart(PurchasedCart purchasedCart) {
		this.purchasedCarts.add(purchasedCart);
		return purchasedCart;
	}

	@JsonProperty("username")
	public String getUsername() {
		return customer.getUsername();
	}

    @JsonProperty("purchaseHistory")
    public List<PurchasedCart> getPurchaseHistory() {
        return this.purchasedCarts;
    }
}
