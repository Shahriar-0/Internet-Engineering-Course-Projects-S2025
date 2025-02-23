package cli.configuration;

import application.repositories.IUserRepository;
import application.services.UserService;
import application.validators.UserValidator;
import infra.repositories.UserRepository;
import lombok.Getter;

@Getter
public class AppContext {
    private final IUserRepository userRepository = new UserRepository();
    private final UserValidator userValidator = new UserValidator(userRepository);
    private final UserService userService = new UserService(userRepository, userValidator);
}
