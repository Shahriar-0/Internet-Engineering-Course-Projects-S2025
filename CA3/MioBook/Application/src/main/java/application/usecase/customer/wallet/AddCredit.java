package application.usecase.customer.wallet;

import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.Customer;
import domain.entities.User;
import jakarta.validation.constraints.Min;

public class AddCredit implements IUseCase {
	@Override
	public UseCaseType getType() {
		return UseCaseType.ADD_CREDIT;
	}

	public Result<Customer> perform(AddCreditData data, User user) {
		assert user instanceof Customer: "we relay on presentation layer access control";
		Customer customer = (Customer) user;

		customer.addCredit(data.amount);
		return Result.success(customer);
	}

	public record AddCreditData(
		@Min(value = 100, message = "Credit amount must be greater or equal to 100 cent")
		long amount
  ) {}
}
