package domain.exceptions.customer;

import domain.exceptions.DomainException;

public class CartIsEmpty extends DomainException {
    private static final String MESSAGE = "Cart is empty!";

    public CartIsEmpty() {
        super(MESSAGE);
    }
}
