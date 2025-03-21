package webapi.configuration;

import application.repositories.*;
import application.usecase.admin.author.AddAuthor;
import application.usecase.admin.book.AddBook;
import application.usecase.customer.book.AddReview;
import application.usecase.customer.book.GetBookContent;
import application.usecase.customer.book.GetPurchasedBooks;
import application.usecase.customer.cart.AddCart;
import application.usecase.customer.cart.BorrowBook;
import application.usecase.customer.cart.GetCart;
import application.usecase.customer.cart.RemoveCart;
import application.usecase.customer.purchase.GetPurchaseHistory;
import application.usecase.customer.purchase.PurchaseCart;
import application.usecase.customer.wallet.AddCredit;
import application.usecase.user.*;
import application.usecase.user.account.CreateAccount;
import application.usecase.user.account.Login;
import application.usecase.user.author.GetAuthor;
import application.usecase.user.book.GetBook;
import application.usecase.user.book.GetBookReviews;
import infra.repositories.*;
import lombok.Getter;

@Getter
public class AppContext {

	private final IUserRepository userRepository = new UserRepository();
	private final IAuthorRepository authorRepository = new AuthorRepository();
	private final IBookRepository bookRepository = new BookRepository();

	/*	----------------------------------- use cases ----------------------------------- */
	private final CreateAccount addUserUseCase = new CreateAccount(userRepository);
	private final AddAuthor addAuthorUseCase = new AddAuthor(authorRepository);
	private final AddBook addBookUseCase = new AddBook(authorRepository, bookRepository);
	private final AddCart addCartUseCase = new AddCart(userRepository, bookRepository);
	private final RemoveCart removeCartUseCase = new RemoveCart(userRepository, bookRepository);
	private final AddCredit addCreditUseCase = new AddCredit(userRepository);
	private final PurchaseCart purchaseCartUseCase = new PurchaseCart(userRepository);
	private final BorrowBook borrowBookUseCase = new BorrowBook(userRepository, bookRepository);
	private final GetUser getUserUseCase = new GetUser(userRepository);
	private final GetAuthor getAuthorUseCase = new GetAuthor(authorRepository);
	private final GetBook getBookUseCase = new GetBook(bookRepository);
	private final AddReview addReviewUseCase = new AddReview(userRepository, bookRepository);
	private final GetBookReviews getBookReviewsUseCase = new GetBookReviews(bookRepository);
	private final GetBookContent getBookContentUseCase = new GetBookContent(userRepository, bookRepository);
	private final GetCart getCartUseCase = new GetCart(userRepository);
	private final GetPurchaseHistory getPurchaseHistoryUseCase = new GetPurchaseHistory(userRepository);
	private final GetPurchasedBooks getPurchasedBooksUseCase = new GetPurchasedBooks(userRepository);
	private final Login loginUseCase = new Login(userRepository);
}
