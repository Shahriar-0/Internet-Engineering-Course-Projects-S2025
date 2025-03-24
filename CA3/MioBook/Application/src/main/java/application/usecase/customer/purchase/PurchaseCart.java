package application.usecase.customer.purchase;

import application.exceptions.businessexceptions.cartexceptions.CantPurchaseCart;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.cart.Cart;
import domain.entities.user.Customer;
import domain.entities.user.User;

public class PurchaseCart implements IUseCase {
	@Override
	public UseCaseType getType() {
		return UseCaseType.PURCHASE_CART;
	}

	public Result<Cart> perform(User user) {
		assert user instanceof Customer: "we rely on presentation layer access control";
		Customer customer = (Customer) user;

		if (!customer.canPurchaseCart())
			return Result.failure(new CantPurchaseCart(customer.findPurchaseCartErrors()));

		Cart purchasedCart = customer.purchaseCart();
		return Result.success(purchasedCart);
	}
}
