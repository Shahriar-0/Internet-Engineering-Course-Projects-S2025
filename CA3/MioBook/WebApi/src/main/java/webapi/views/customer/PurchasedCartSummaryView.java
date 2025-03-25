package webapi.views.customer;

import domain.entities.cart.Cart;

import java.time.LocalDateTime;

public record PurchasedCartSummaryView(
    LocalDateTime datePurchased,
    long totalCost,
    int bookCount)
{
    public PurchasedCartSummaryView(Cart purchasedCart) {
        this (
            purchasedCart.getPurchaseDate(),
            purchasedCart.getTotalCost(),
            purchasedCart.getItems().size()
        );
    }
}
