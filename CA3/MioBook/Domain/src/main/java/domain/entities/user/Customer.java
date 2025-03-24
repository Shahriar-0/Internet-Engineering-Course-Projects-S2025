package domain.entities.user;

import domain.entities.book.Book;
import domain.entities.booklicense.BookLicense;
import domain.entities.cart.Cart;
import domain.valueobjects.Address;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@SuperBuilder
public class Customer extends User {
	private static final int FIRST_CART_ID = 1;
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

	public Boolean canPurchaseCart() {
		return cart.findPurchaseCartErrors(credit) == null;
	}

	/**
	 * @return A string containing the reason why the cart cannot be purchased.
	 *         If the cart can be purchased, this method returns null.
	 */
	public String findPurchaseCartErrors() {
		return cart.findPurchaseCartErrors(credit);
	}

	public void addCredit(long amount) {
		if (amount < 0)
			throw new RuntimeException("Amount must be positive.");

		credit += amount;
	}

	public Cart purchaseCart() {
		if (!canPurchaseCart())
			throw new RuntimeException(findPurchaseCartErrors());

		credit -= cart.getTotalCost();
		cart.purchase();
		Cart purchasedCart = cart;
        purchasedLicenses.addAll(cart.getLicenses());
		cart = new Cart(this, purchaseHistory.size() + 1);
		purchaseHistory.add(purchasedCart);
		return purchasedCart;
	}

	public Boolean hasAccess(String bookTitle) {
		for (BookLicense license : purchasedLicenses)
			if (license.getBook().isKeyEqual(bookTitle))
				return license.isValid();

		return false;
	}
}
