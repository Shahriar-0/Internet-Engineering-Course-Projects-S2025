package application.usecase.user;

import application.exceptions.businessexceptions.userexceptions.UserNotFound;
import application.exceptions.businessexceptions.userexceptions.WrongPassword;
import application.repositories.IUserRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.User;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LoginUseCase implements IUseCase {

	private final IUserRepository userRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.LOGIN;
	}

	public Result<User> perform(LoginData data) {
		assert (data.username != null && !data.username.isBlank()) ||
		(data.email != null && !data.email.isBlank()) : "we relay on valid input data, validation should be done in presentation layer";

		if (data.username != null && !data.username.isBlank())
			return loginByUsername(data.username, data.password);
		else
			return loginByEmail(data.email,data.password);
	}

	private Result<User> loginByUsername(String username, String password) {
		Result<User> userResult = userRepository.find(username);
		if (userResult.isFailure())
			return Result.failure(UserNotFound.usernameNotFound(username));

		if (!userResult.getData().getPassword().equals(password))
			return Result.failure(WrongPassword.usernameNotFound(username));

		return userResult;
	}

	private Result<User> loginByEmail(String email, String password) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isEmpty())
			return Result.failure(UserNotFound.emailNotFound(email));

		if (!optionalUser.get().getPassword().equals(password))
			return Result.failure(WrongPassword.emailNotFound(email));

		return Result.success(optionalUser.get());
	}

	public record LoginData(String username, @Email String email, @NotBlank String password) {
		@AssertTrue(message = "Both email and username can't be blank")
		private boolean isBothEmailAndUsernameBlank() {
			try {
				return (username != null && !username.isBlank()) || (email != null && !email.isBlank());
			}
			catch (Exception e) {
				return false;
			}
		}
	}
}
