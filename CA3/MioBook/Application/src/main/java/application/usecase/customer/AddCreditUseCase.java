package application.usecase.customer;

import application.repositories.IUserRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.Customer;
import domain.entities.User;
import jakarta.validation.constraints.Min;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddCreditUseCase implements IUseCase {

	private final IUserRepository userRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.ADD_CREDIT;
	}

	public Result<Customer> perform(AddCreditData data, String username) {
		Result<User> userResult = userRepository.get(username);
		if (userResult.isFailure())
			return Result.failure(userResult.getException());
		assert userResult.getData() instanceof Customer : "we relay on role passing from presentation layer";
		Customer customer = (Customer) userResult.getData();

		customer.addCredit(data.credit);
		return Result.success(customer);
	}

	public record AddCreditData(
		@Min(value = 100, message = "Credit amount must be greater or equal to 100 cent")
		long credit
  ) {}
}
