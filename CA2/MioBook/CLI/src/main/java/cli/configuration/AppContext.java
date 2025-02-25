package cli.configuration;

import application.repositories.IUserRepository;
import application.services.UserService;
import application.validators.UserValidator;
import cli.inputprocessors.CommandGenerator;
import cli.outputprocessors.CliWriter;
import infra.repositories.UserRepository;
import lombok.Getter;

@Getter
public class AppContext {
    private final IUserRepository userRepository = new UserRepository();
    private final UserValidator userValidator = new UserValidator(userRepository);
    private final UserService userService = new UserService(userRepository, userValidator);

    private final CommandGenerator commandGenerator = new CommandGenerator(userService);
    private final CliWriter cliWriter = new CliWriter();
}
