package webapi.views.customer;

import domain.valueobjects.PurchaseHistory;

import java.util.List;

public record PurchaseHistoryView(
    String username,
    List<PurchasedCartView> purchaseHistory
) {
    public PurchaseHistoryView(PurchaseHistory history) {
        this(
            history.getUsername(),
            history.getPurchaseHistory().stream().map(PurchasedCartView::new).toList()
        );
    }
}
