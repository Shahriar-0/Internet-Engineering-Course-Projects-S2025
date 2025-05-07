package application.usecase.user.account;

import application.exceptions.businessexceptions.userexceptions.EmailAlreadyExists;
import application.exceptions.businessexceptions.userexceptions.UsernameAlreadyExists;
import application.repositories.IUserRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.user.Admin;
import domain.entities.user.Customer;
import domain.entities.user.Role;
import domain.entities.user.User;
import domain.valueobjects.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAccount implements IUseCase {

	private final IUserRepository userRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.CREATE_ACCOUNT;
	}

	public Result<User> perform(AddUserData data) {
		if (userRepository.existsByUsername(data.username))
			return Result.failure(new UsernameAlreadyExists(data.username));

		if (userRepository.existsByEmail(data.email))
			return Result.failure(new EmailAlreadyExists(data.email));

		return Result.success(userRepository.save(mapToUser(data)));
	}

	public static User mapToUser(AddUserData data) {
		Role role = Role.valueOf(data.role.toUpperCase());

        if (role == Role.CUSTOMER)
			return new Customer(data.username, data.password, data.email, data.address);
		else
			return new Admin(data.username, data.password, data.email, data.address);
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
