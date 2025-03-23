package application.usecase.user;

import application.repositories.IUserRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetUser implements IUseCase {

	private final IUserRepository userRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.GET_USER;
	}

	public Result<User> perform(String username) {
		assert username != null && !username.isBlank() : "we rely on presentation layer validation for field username";

		return userRepository.find(username);
	}

	public Result<List<User>> perform(UserFilter filter) {
		throw new RuntimeException("Not implemented yet");
	}

	public record UserFilter() {}
}
