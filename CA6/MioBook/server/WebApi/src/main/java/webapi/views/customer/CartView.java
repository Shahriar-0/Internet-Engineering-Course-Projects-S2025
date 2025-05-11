package webapi.views.customer;

import domain.entities.cart.Cart;

import java.util.List;

public record CartView(
    String username,
    Long totalCost,
    List<CustomerBookView> items
) {
    public CartView(Cart cart) {
        this(
            cart.getCustomer().getUsername(),
            cart.getTotalCost(),
            cart.getItems().stream().map(CustomerBookView::new).toList()
        );
    }
}
