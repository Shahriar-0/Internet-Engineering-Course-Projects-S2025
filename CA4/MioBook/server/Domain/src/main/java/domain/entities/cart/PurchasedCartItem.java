package domain.entities.cart;

import domain.entities.DomainEntity;
import domain.entities.book.Book;
import domain.entities.user.Customer;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public class PurchasedCartItem extends DomainEntity<PurchasedCartItem.Key> {

    private final Cart cart;
    private final Customer customer;
    private final Book book;
    private final boolean isBorrow;
    private final int borrowDays;
    private final long price;

    public PurchasedCartItem(CartItem item, long cartId) {
        super(new Key(item.getCustomer().getUsername(), cartId, item.getBook().getTitle()));
        this.cart = item.getCart();
        this.customer = item.getCustomer();
        this.book = item.getBook();
        this.isBorrow = item.isBorrow();
        this.borrowDays = item.getBorrowDays();
        this.price = item.getPrice();
    }

    public record Key(String username, long cartId, String bookTitle) {}
}
