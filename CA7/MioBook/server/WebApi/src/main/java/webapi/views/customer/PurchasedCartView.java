package webapi.views.customer;

import domain.entities.cart.PurchasedCart;
import java.time.LocalDateTime;
import java.util.List;

public record PurchasedCartView(
    LocalDateTime datePurchased,
    Long totalCost,
    List<CustomerBookView> books
) {
    public PurchasedCartView(PurchasedCart cart) {
        this(
            cart.getPurchaseDateTime(),
            cart.getTotalCost(),
            cart.getItems().stream().map(CustomerBookView::new).toList()
        );
    }
}