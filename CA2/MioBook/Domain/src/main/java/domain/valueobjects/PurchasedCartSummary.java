package domain.valueobjects;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class PurchasedCartSummary {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private final LocalDateTime datePurchased;

	private final long totalCost;

	private final int bookCount;

	public PurchasedCartSummary(PurchasedCart cart) {
		this.datePurchased = cart.getDatePurchased();
		this.totalCost = cart.getTotalCost();
		this.bookCount = cart.getBooks().size();
	}
}
