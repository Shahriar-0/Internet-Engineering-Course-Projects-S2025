package application.usecase.customer.cart;

import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.Customer;
import domain.entities.User;
import domain.valueobjects.Cart;

public class GetCart implements IUseCase {
	@Override
	public UseCaseType getType() {
		return UseCaseType.GET_CART;
	}

	public Result<Cart> perform(User user) {
		assert user instanceof Customer: "we relay on presentation layer access control";
		Customer customer = (Customer) user;

		return Result.success(customer.getCart());
	}
}
