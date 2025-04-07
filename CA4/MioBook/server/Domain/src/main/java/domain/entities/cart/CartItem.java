package domain.entities.cart;

import domain.entities.DomainEntity;
import domain.entities.book.Book;
import domain.entities.user.Customer;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class CartItem extends DomainEntity<CartItem.Key> {
    public static final int MIN_BORROW_DAYS = 1;
    public static final int MAX_BORROW_DAYS = 10;

    private final Cart cart;
    private final Customer customer;
    private final Book book;
    private final boolean isBorrow;
    private final int borrowDays;

    private CartItem(Key key, Cart cart, Customer customer, Book book, boolean isBorrow, int validityDays) {
        super(key);
        this.cart = cart;
        this.customer = customer;
        this.book = book;
        this.isBorrow = isBorrow;
        this.borrowDays = validityDays;
        assert !isBorrow || (validityDays <= MAX_BORROW_DAYS && validityDays >= MIN_BORROW_DAYS);
    }

    public static CartItem createBorrowingItem(Cart cart, Customer customer, Book book, int validityDays) {
        Key key = new Key(customer.getKey(), book.getKey());
        return new CartItem(key, cart, customer, book, true, validityDays);
    }

    public static CartItem createPermanentItem(Cart cart, Customer customer, Book book) {
        Key key = new Key(customer.getKey(), book.getKey());
        return new CartItem(key, cart, customer, book, false, 0);
    }

    public long getPrice() {
        if (isBorrow)
            return book.getBasePrice() * borrowDays / MAX_BORROW_DAYS;
        else
            return book.getBasePrice();
    }

    public record Key(String customerName, String bookTitle) {};
}
