package domain.entities.cart;

import domain.entities.DomainEntity;
import domain.entities.book.Book;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class CartItem extends DomainEntity {
    public static final int MIN_BORROW_DAYS = 1;
    public static final int MAX_BORROW_DAYS = 10;

    private Cart cart;
    private Book book;
    private boolean isBorrowed;
    private Integer borrowDays;

    private CartItem(Cart cart, Book book, boolean isBorrowed, int validityDays) {
        this.cart = cart;
        this.book = book;
        this.isBorrowed = isBorrowed;
        this.borrowDays = validityDays;
        assert !isBorrowed || (validityDays <= MAX_BORROW_DAYS && validityDays >= MIN_BORROW_DAYS);
    }

    public static CartItem createBorrowingItem(Cart cart, Book book, int validityDays) {
        return new CartItem(cart, book, true, validityDays);
    }

    public static CartItem createPermanentItem(Cart cart, Book book) {
        return new CartItem(cart, book, false, 0);
    }

    public long getPrice() {
        if (isBorrowed)
            return book.getBasePrice() * borrowDays / MAX_BORROW_DAYS;
        else
            return book.getBasePrice();
    }
}
