package domain.valueobjects;

import domain.entities.cart.Cart;
import lombok.Getter;

import java.time.LocalDateTime;

// TODO: move this to view
@Getter
public class PurchasedCartSummary {

	private final LocalDateTime datePurchased;
	private final long totalCost;
	private final int bookCount;

	public PurchasedCartSummary(Cart cart) {
		this.datePurchased = cart.getPurchaseDate();
		this.totalCost = cart.getTotalCost();
		this.bookCount = cart.getLicenses().size();
	}
}
