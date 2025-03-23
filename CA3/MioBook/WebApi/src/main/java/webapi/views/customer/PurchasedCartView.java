package webapi.views.customer;

import domain.valueobjects.PurchasedCart;

import java.time.LocalDateTime;
import java.util.List;

public record PurchasedCartView(
    LocalDateTime datePurchased,
    Long totalCost,
    List<CustomerBookView> books
) {
    public PurchasedCartView(PurchasedCart cart) {
        this(
            cart.getDatePurchased(),
            cart.getTotalCost(),
            cart.getBooks().stream().map(CustomerBookView::new).toList()
        );
    }
}