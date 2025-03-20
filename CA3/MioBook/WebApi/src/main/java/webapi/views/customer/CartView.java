package webapi.views.customer;

import java.util.List;
import domain.valueobjects.Cart;

public record CartView(
    String username,
    Long totalCost,
    List<CustomerBookView> items
) {
    public CartView(Cart cart) {
        this(
            cart.getUsername(),
            cart.getTotalCost(),
            cart.getBooks().stream().map(CustomerBookView::new).toList()
        );
    }
}
