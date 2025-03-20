package application.usecase.user;

import application.repositories.IUserRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.User;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetUserUseCase implements IUseCase {

	private final IUserRepository userRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.GET_USER;
	}

	public Result<User> perform(String username) {
		assert username != null && !username.isBlank() : "we relay on @NotBlank validation on username field in presentation layer";

		return userRepository.find(username);
	}

	// TODO: this feature added soon
	public Result<List<User>> perform(UserFilter filter) {
		throw new RuntimeException("Not implemented yet");
	}

	// TODO: this feature added soon
	public record UserFilter() {}
}
