package application.usecase.customer;

import application.exceptions.businessexceptions.cartexceptions.CantPurchaseCart;
import application.repositories.IUserRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.Customer;
import domain.entities.User;
import domain.valueobjects.PurchasedCart;
import domain.valueobjects.PurchasedCartSummary;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PurchaseCartUseCase implements IUseCase {

	private final IUserRepository userRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.PURCHASE_CART;
	}

	public Result<PurchasedCartSummary> perform(String username) {
		Result<User> userResult = userRepository.get(username);
		if (userResult.isFailure())
			return Result.failure(userResult.getException());
		assert userResult.getData() instanceof Customer : "we relay on role passing from presentation layer";
		Customer customer = (Customer) userResult.getData();

		if (!customer.canPurchaseCart())
			return Result.failure(new CantPurchaseCart(customer.findPurchaseCartErrors()));

		PurchasedCart purchasedCart = customer.purchaseCart();
		return Result.success(new PurchasedCartSummary(purchasedCart));
	}
}
