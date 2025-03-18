package application.usecase.user;

import application.exceptions.businessexceptions.userexceptions.EmailAlreadyExists;
import application.exceptions.businessexceptions.userexceptions.UsernameAlreadyExists;
import application.repositories.IUserRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.Admin;
import domain.entities.Customer;
import domain.entities.User;
import domain.valueobjects.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AddUserUseCase implements IUseCase {

	private static final int DEFAULT_CREDIT_AT_CREATION = 0;

	private final IUserRepository userRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.ADD_USER;
	}

	public Result<User> perform(AddUserData data) {
		if (userRepository.exists(data.username))
			return Result.failure(new UsernameAlreadyExists(data.username));

		if (userRepository.doesEmailExist(data.email))
			return Result.failure(new EmailAlreadyExists(data.email));

		return userRepository.add(mapToUser(data));
	}

	private static User mapToUser(AddUserData data) {
		User.Role role = User.Role.valueOf(data.role.toUpperCase());
		assert role != null : "Correctness of role field should be checked in presentation layer";

		if (role == User.Role.CUSTOMER)
			return Customer
				.builder()
				.key(data.username)
				.address(data.address)
				.password(data.password)
				.email(data.email)
				.role(role)
				.credit(DEFAULT_CREDIT_AT_CREATION)
				.build();
		else
			return Admin
				.builder()
				.key(data.username)
				.address(data.address)
				.password(data.password)
				.email(data.email)
				.role(role)
				.build();
	}

	public record AddUserData(
		@NotBlank
		@Pattern(regexp = "^(customer|admin)$", message = "Role must be either 'customer' or 'admin'")
		String role,

		@NotBlank
		@Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Username must contain only letters, numbers, underscores, hyphens, or underscores")
		String username,

		@NotBlank
		@Size(min = 4)
		String password,

		@NotBlank
		@Email
		String email,

		@NotNull
		@Valid
		Address address
	) {}
}
