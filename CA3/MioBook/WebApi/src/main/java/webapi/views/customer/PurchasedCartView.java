package webapi.views.customer;

import domain.entities.cart.Cart;

import java.time.LocalDateTime;
import java.util.List;

public record PurchasedCartView(
    LocalDateTime datePurchased,
    Long totalCost,
    List<CustomerBookView> books
) {
    public PurchasedCartView(Cart cart) {
        this(
            cart.getPurchaseDate(),
            cart.getTotalCost(),
            cart.getLicenses().stream().map(CustomerBookView::new).toList()
        );
    }
}