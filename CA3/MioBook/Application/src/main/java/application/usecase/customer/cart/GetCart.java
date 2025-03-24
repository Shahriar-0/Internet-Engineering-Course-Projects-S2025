package application.usecase.customer.cart;

import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.user.Customer;
import domain.entities.user.User;
import domain.entities.cart.Cart;

public class GetCart implements IUseCase {
	@Override
	public UseCaseType getType() {
		return UseCaseType.GET_CART;
	}

	public Result<Cart> perform(User user) {
		assert user instanceof Customer: "we rely on presentation layer access control";
		Customer customer = (Customer) user;

		return Result.success(customer.getCart());
	}
}
