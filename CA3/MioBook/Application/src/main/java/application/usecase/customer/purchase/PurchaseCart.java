package application.usecase.customer.purchase;

import application.exceptions.businessexceptions.cartexceptions.CantPurchaseCart;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.Customer;
import domain.entities.User;
import domain.valueobjects.PurchasedCart;
import domain.valueobjects.PurchasedCartSummary;

public class PurchaseCart implements IUseCase {
	@Override
	public UseCaseType getType() {
		return UseCaseType.PURCHASE_CART;
	}

	public Result<PurchasedCartSummary> perform(User user) {
		assert user instanceof Customer: "we relay on presentation layer access control";
		Customer customer = (Customer) user;

		if (!customer.canPurchaseCart())
			return Result.failure(new CantPurchaseCart(customer.findPurchaseCartErrors()));

		PurchasedCart purchasedCart = customer.purchaseCart();
		return Result.success(new PurchasedCartSummary(purchasedCart));
	}
}
