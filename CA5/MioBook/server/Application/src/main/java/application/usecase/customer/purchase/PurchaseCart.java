package application.usecase.customer.purchase;

import application.exceptions.businessexceptions.cartexceptions.CantPurchaseCart;
import application.repositories.IUserRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.cart.PurchasedCart;
import domain.entities.user.Customer;
import domain.entities.user.User;
import domain.exceptions.DomainException;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PurchaseCart implements IUseCase {

	private final IUserRepository userRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.PURCHASE_CART;
	}

	public Result<PurchasedCart> perform(User user) {
		assert user instanceof Customer: "we rely on presentation layer access control";
		Customer customer = (Customer) user;

        List<DomainException> exceptions = customer.getPurchaseCartErrors();
		if (!exceptions.isEmpty())
			return Result.failure(new CantPurchaseCart(exceptions.getFirst().getMessage()));

		PurchasedCart purchasedCart = customer.purchaseCart();
		userRepository.purchase(customer, purchasedCart);
		return Result.success(purchasedCart);
	}
}
