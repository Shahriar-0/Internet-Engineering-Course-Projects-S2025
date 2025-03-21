package application.usecase.customer;

import application.exceptions.businessexceptions.userexceptions.InvalidAccess;
import application.repositories.IUserRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.Customer;
import domain.entities.User;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AddCreditUseCase implements IUseCase {

	private final IUserRepository userRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.ADD_CREDIT;
	}

	public Result<Customer> perform(AddCreditData data, String username, User.Role role) {
		if (!User.Role.CUSTOMER.equals(role))
			return Result.failure(new InvalidAccess("customer"));

		Result<User> userResult = userRepository.get(username);
		if (userResult.isFailure())
			return Result.failure(userResult.exception());
		assert userResult.data() instanceof Customer : "we relay on role passing from presentation layer";
		Customer customer = (Customer) userResult.data();

		customer.addCredit(data.amount);
		return Result.success(customer);
	}

	public record AddCreditData(
		@Min(value = 100, message = "Credit amount must be greater or equal to 100 cent")
		long amount
  ) {}
}
