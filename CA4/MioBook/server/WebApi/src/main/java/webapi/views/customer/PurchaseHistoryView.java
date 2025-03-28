package webapi.views.customer;

import domain.entities.cart.Cart;

import java.util.List;

public record PurchaseHistoryView(
    String username,
    List<PurchasedCartView> purchaseHistory
) {
    public PurchaseHistoryView(List<Cart> history, String username) {
        this(
            username,
            history.stream().map(PurchasedCartView::new).toList()
        );
    }
}
