package domain.exceptions.cart;

import domain.entities.cart.Cart;
import domain.exceptions.DomainException;

public class CartIsFull extends DomainException {
    private static final String MESSAGE = "Cart is full! Cannot add more books. Maximum books: " + Cart.MAXIMUM_BOOKS;

    public CartIsFull() {
        super(MESSAGE);
    }
}
