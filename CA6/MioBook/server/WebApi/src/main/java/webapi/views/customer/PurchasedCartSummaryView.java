package webapi.views.customer;

import domain.entities.cart.PurchasedCart;

import java.time.LocalDateTime;

public record PurchasedCartSummaryView(
    LocalDateTime datePurchased,
    long totalCost,
    int bookCount)
{
    public PurchasedCartSummaryView(PurchasedCart purchasedCart) {
        this (
            purchasedCart.getPurchaseDateTime(),
            purchasedCart.getTotalCost(),
            purchasedCart.getItems().size()
        );
    }
}
