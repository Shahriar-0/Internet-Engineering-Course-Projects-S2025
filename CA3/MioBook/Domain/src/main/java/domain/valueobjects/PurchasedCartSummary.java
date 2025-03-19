package domain.valueobjects;

import java.time.LocalDateTime;
import lombok.Getter;

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
