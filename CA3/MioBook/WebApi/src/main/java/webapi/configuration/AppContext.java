package webapi.configuration;

import application.repositories.*;
import application.usecase.admin.*;
import application.usecase.customer.*;
import application.usecase.user.*;
import infra.repositories.*;
import lombok.Getter;

@Getter
public class AppContext {

	private final IUserRepository userRepository = new UserRepository();
	private final IAuthorRepository authorRepository = new AuthorRepository();
	private final IBookRepository bookRepository = new BookRepository();

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
	private final GetBookUseCase getBookUseCase = new GetBookUseCase(bookRepository);
	private final AddReviewUseCase addReviewUseCase = new AddReviewUseCase(userRepository, bookRepository);
	private final GetBookReviewsUseCase getBookReviewsUseCase = new GetBookReviewsUseCase(bookRepository);
	private final GetBookContentUseCase getBookContentUseCase = new GetBookContentUseCase(userRepository, bookRepository);
	private final GetCartUseCase getCartUseCase = new GetCartUseCase(userRepository);
	private final GetPurchaseHistoryUseCase getPurchaseHistoryUseCase = new GetPurchaseHistoryUseCase(userRepository);
	private final GetPurchasedBooksUseCase getPurchasedBooksUseCase = new GetPurchasedBooksUseCase(userRepository);
	private final LoginUseCase loginUseCase = new LoginUseCase(userRepository);
}
