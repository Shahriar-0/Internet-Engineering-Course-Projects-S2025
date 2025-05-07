package application.usecase.customer.wallet;

import application.repositories.IUserRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.user.Customer;
import domain.entities.user.User;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddCredit implements IUseCase {

	private final IUserRepository userRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.ADD_CREDIT;
	}

	public Result<Customer> perform(AddCreditData data, User user) {
		assert user instanceof Customer: "we rely on presentation layer access control";
		Customer customer = (Customer) user;

		customer.addCredit(data.amount);
		userRepository.update(customer);
		return Result.success(customer);
	}

	public record AddCreditData(
		@Min(value = 100, message = "Credit amount must be greater or equal to 100 cent")
		long amount
  ) {}
}
