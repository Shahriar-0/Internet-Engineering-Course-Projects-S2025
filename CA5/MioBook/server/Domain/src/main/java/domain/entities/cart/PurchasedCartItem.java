package domain.entities.cart;

import domain.entities.DomainEntity;
import domain.entities.book.Book;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class PurchasedCartItem extends DomainEntity {

    private final Book book;
    private final boolean isBorrowed;
    private final Integer borrowDays;
    private final Long price;

    public PurchasedCartItem(CartItem item) {
        this.book = item.getBook();
        this.isBorrowed = item.isBorrowed();
        this.borrowDays = item.getBorrowDays();
        this.price = item.getPrice();
    }
}
