package cli.configuration;

import application.repositories.*;
import application.services.*;
import application.validators.*;
import cli.inputprocessors.CommandGenerator;
import cli.outputprocessors.CliWriter;
import infra.repositories.*;
import lombok.Getter;

@Getter
public class AppContext {

	private final IUserRepository userRepository = new UserRepository();
	private final IAuthorRepository authorRepository = new AuthorRepository();
	private final IBookRepository bookRepository = new BookRepository();

	private final UserValidator userValidator = new UserValidator(userRepository);
	private final AuthorValidator authorValidator = new AuthorValidator(authorRepository);
	private final BookValidator bookValidator = new BookValidator(bookRepository, authorValidator);

	private final UserService userService = new UserService(userRepository, userValidator, bookRepository);
	private final AdminService adminService = new AdminService(
		authorValidator,
		authorRepository,
		bookValidator,
		bookRepository,
		userService
	);

	private final CommandGenerator commandGenerator = new CommandGenerator(userService, adminService);
	private final CliWriter cliWriter = new CliWriter();
}
