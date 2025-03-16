package application.uscase.user;

import application.repositories.IUserRepository;
import application.result.Result;
import application.uscase.IUseCase;
import application.uscase.UseCaseType;
import domain.entities.User;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetUserUseCase implements IUseCase {
    private final IUserRepository userRepository;

    @Override
    public UseCaseType getType() {
        return UseCaseType.GET_USER;
    }

    public Result<User> perform(String username) {
        return userRepository.find(username);
    }

    // TODO: this feature added soon
    public Result<List<User>> perform(UserFilter filter) {
        throw new RuntimeException("Not implemented yet");
    }

    // TODO: this feature added soon
    public record UserFilter(

    ) {}
}
