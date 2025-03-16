package webapi.configuration;

import application.dtos.RemoveCartDto;
import application.repositories.*;
import application.services.*;
import application.uscase.admin.AddAuthorUseCase;
import application.uscase.admin.AddBookUseCase;
import application.uscase.customer.*;
import application.uscase.user.AddUserUseCase;
import application.uscase.user.GetAuthorUseCase;
import application.uscase.user.GetUserUseCase;
import application.validators.*;
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

	private final UserService userService = new UserService(userRepository, userValidator, bookRepository, authorRepository);
	private final AdminService adminService = new AdminService(
		authorValidator,
		authorRepository,
		bookValidator,
		bookRepository,
		userService
	);


/*	----------------------------------- use cases ----------------------------------- */
	private final AddUserUseCase addUserUseCase = new AddUserUseCase(userRepository);
	private final AddAuthorUseCase addAuthorUseCase = new AddAuthorUseCase(authorRepository);
	private final AddBookUseCase addBookUseCase = new AddBookUseCase(authorRepository, bookRepository);
	private final AddCartUseCase addCartUseCase = new AddCartUseCase(userRepository, bookRepository);
	private final RemoveCartUseCase removeCartUseCase = new RemoveCartUseCase(userRepository, bookRepository);
	private final AddCreditUseCase addCreditUseCase = new AddCreditUseCase(userRepository);
	private final PurchaseCartUseCase purchaseCartUseCase = new PurchaseCartUseCase(userRepository);
	private final BorrowBookUseCase borrowBookUseCase = new BorrowBookUseCase(userRepository, bookRepository);
	private final GetUserUseCase getUserUseCase = new GetUserUseCase(userRepository);
	private final GetAuthorUseCase getAuthorUseCase = new GetAuthorUseCase(authorRepository);
}
