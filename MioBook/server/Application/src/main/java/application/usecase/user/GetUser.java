package application.usecase.user;

import application.exceptions.businessexceptions.userexceptions.UserNotFound;
import application.repositories.IUserRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.user.User;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetUser implements IUseCase {

	private final IUserRepository userRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.GET_USER;
	}

	public Result<User> perform(String username) {
		assert username != null && !username.isBlank() : "we rely on presentation layer validation for field username";

		Optional<User> user = userRepository.findByUsername(username);
        return user.map(Result::success).orElseGet(() -> Result.failure(UserNotFound.usernameNotFound(username)));
    }

	public Result<List<User>> perform(UserFilter filter) {
		List<User> users = userRepository.filter(filter);
		return Result.success(users);
	}

	public record UserFilter(String name, String email, String role) {}
}
