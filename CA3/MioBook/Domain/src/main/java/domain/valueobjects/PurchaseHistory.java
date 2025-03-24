package domain.valueobjects;

import domain.entities.user.Customer;
import lombok.Getter;
import java.util.ArrayList;
import java.util.List;

//  TODO: move this into customer
@Getter
public class PurchaseHistory {

	private final Customer customer;
	private final List<PurchasedCart> purchasedCarts;

	public PurchaseHistory(Customer customer) {
		this.customer = customer;
		this.purchasedCarts = new ArrayList<>();
	}

	public PurchasedCart addPurchasedCart(PurchasedCart purchasedCart) {
		this.purchasedCarts.add(purchasedCart);
		return purchasedCart;
	}

	public String getUsername() {
		return customer.getUsername();
	}

	public List<PurchasedCart> getPurchaseHistory() {
		return this.purchasedCarts;
	}

	public Boolean hasBook(String title) {
		return this.purchasedCarts.stream().anyMatch(p -> p.hasBook(title));
	}

	public List<CustomerBook> getAccessibleBooks() {
		return this.purchasedCarts.stream().flatMap(ph -> ph.getAccessibleBooks().stream()).toList();
	}
}
