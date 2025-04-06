package domain.entities.user;

import domain.entities.booklicense.BookLicense;
import domain.entities.booklicense.TemporaryBookLicense;
import domain.entities.booklicense.PermanentBookLicense;
import domain.entities.cart.Cart;
import domain.entities.cart.CartItem;
import domain.exceptions.DomainException;
import domain.exceptions.customer.CartIsEmpty;
import domain.exceptions.customer.NotEnoughCredit;
import domain.valueobjects.Address;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
public class Customer extends User {
	public static final int INITIAL_CREDIT_AMOUNT = 0;

	private long credit;
	private Cart cart;
	private final List<BookLicense> purchasedLicenses = new ArrayList<>();
	private final List<Cart> purchaseHistory = new ArrayList<>();

	public Customer(String username, String password, String email, Address address) {
		super(username, password, email, address, Role.CUSTOMER);
		this.cart = new Cart(this, 1);
		this.credit = 0;
	}

	public List<BookLicense> getValidLicenses() {
		return purchasedLicenses.stream()
				.filter(BookLicense::isValid)
				.toList();
	}

	public List<DomainException> getPurchaseCartErrors() {
        List<DomainException> exceptions = new ArrayList<>();

        if (cart.getItems().isEmpty())
            exceptions.add(new CartIsEmpty());

        long cartTotalCost = cart.getTotalCost();
        if (credit < cartTotalCost)
            exceptions.add(new NotEnoughCredit(cartTotalCost, credit));

		return exceptions;
	}

	public void addCredit(long amount) {
		assert amount > 0;
		credit += amount;
	}

	public Cart purchaseCart() {
		assert getPurchaseCartErrors().isEmpty();

        LocalDateTime purchaseDate = LocalDateTime.now();
		cart.setPurchaseDate(purchaseDate);
        cart.getItems().forEach(item -> addCartItemToLicenses(item, purchaseDate));
		credit -= cart.getTotalCost();
		purchaseHistory.add(cart);
		Cart purchasedCart = cart;
		cart = new Cart(this, purchaseHistory.size() + 1);
		return purchasedCart;
	}

    private void addCartItemToLicenses(CartItem item, LocalDateTime purchaseDate) {
        BookLicense license;
        long id = purchasedLicenses.size() + 1;
        if (item.isBorrow())
            license = new TemporaryBookLicense(
                this,
                id,
                item.getBook(),
                item.getPrice(),
                purchaseDate,
                item.getBorrowDays()
            );
        else
            license = new PermanentBookLicense(
                this,
                id,
                item.getBook(),
                item.getPrice(),
                purchaseDate
            );

        purchasedLicenses.add(license);
    }

	public Boolean hasAccess(String bookTitle) {
		for (BookLicense license : purchasedLicenses)
			if (license.getBook().isKeyEqual(bookTitle) && license.isValid())
				return true;

		return false;
	}
}
