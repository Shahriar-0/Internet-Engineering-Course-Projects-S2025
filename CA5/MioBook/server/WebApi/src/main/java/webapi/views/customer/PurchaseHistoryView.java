package webapi.views.customer;

import domain.entities.cart.PurchasedCart;

import java.util.List;

public record PurchaseHistoryView(
    String username,
    List<PurchasedCartView> purchaseHistory
) {
    public PurchaseHistoryView(List<PurchasedCart> history, String username) {
        this(
            username,
            history.stream().map(PurchasedCartView::new).toList()
        );
    }
}
