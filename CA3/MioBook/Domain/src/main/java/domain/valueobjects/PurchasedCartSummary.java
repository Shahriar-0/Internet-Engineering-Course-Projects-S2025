package domain.valueobjects;

import lombok.Getter;

import java.time.LocalDateTime;

// TODO: move this to view
@Getter
public class PurchasedCartSummary {

	private final LocalDateTime datePurchased;
	private final long totalCost;
	private final int bookCount;

	public PurchasedCartSummary(PurchasedCart cart) {
		this.datePurchased = cart.getDatePurchased();
		this.totalCost = cart.getTotalCost();
		this.bookCount = cart.getBooks().size();
	}
}
