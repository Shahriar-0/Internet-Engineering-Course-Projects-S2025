package webapi.views.customer;

import domain.valueobjects.Cart;

import java.util.List;

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
