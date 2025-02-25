package cli.configuration;

import application.repositories.IAuthorRepository;
import application.repositories.IUserRepository;
import application.services.AdminService;
import application.services.UserService;
import application.validators.AuthorValidator;
import application.validators.UserValidator;
import cli.inputprocessors.CommandGenerator;
import cli.outputprocessors.CliWriter;
import infra.repositories.AuthorRepository;
import infra.repositories.UserRepository;
import lombok.Getter;

@Getter
public class AppContext {
    private final IUserRepository userRepository = new UserRepository();
    private final IAuthorRepository authorRepository = new AuthorRepository();

    private final UserValidator userValidator = new UserValidator(userRepository);
    private final AuthorValidator authorValidator = new AuthorValidator(authorRepository);

    private final UserService userService = new UserService(userRepository, userValidator);
    private final AdminService adminService = new AdminService(authorValidator, authorRepository, userService);

    private final CommandGenerator commandGenerator = new CommandGenerator(userService, adminService);
    private final CliWriter cliWriter = new CliWriter();
}
