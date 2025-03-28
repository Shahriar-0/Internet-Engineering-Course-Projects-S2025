package webapi.configuration;

import application.repositories.IAuthorRepository;
import application.repositories.IBookRepository;
import application.repositories.IUserRepository;
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
import application.usecase.user.GetUser;
import application.usecase.user.account.CreateAccount;
import application.usecase.user.account.Login;
import application.usecase.user.author.GetAuthor;
import application.usecase.user.book.GetBook;
import application.usecase.user.book.GetBookReviews;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	private final AppContext context = new AppContext();

	@Bean
	public IUserRepository userRepository() {
		return context.getUserRepository();
	}

	@Bean
	public IAuthorRepository authorRepository() {
		return context.getAuthorRepository();
	}

	@Bean
	public IBookRepository bookRepository() {
		return context.getBookRepository();
	}
	@Bean
	public CreateAccount addUserUseCase() {
		return context.getAddUserUseCase();
	}

	@Bean
	public AddAuthor addAuthorUseCase() {
		return context.getAddAuthorUseCase();
	}

	@Bean
	public AddBook addBookUseCase() {
		return context.getAddBookUseCase();
	}

	@Bean
	public AddCart addCartUseCase() {
		return context.getAddCartUseCase();
	}

	@Bean
	public RemoveCart removeCartUseCase() {
		return context.getRemoveCartUseCase();
	}

	@Bean
	public AddCredit addCreditUseCase() {
		return context.getAddCreditUseCase();
	}

	@Bean
	public PurchaseCart purchaseCartUseCase() {
		return context.getPurchaseCartUseCase();
	}

	@Bean
	public BorrowBook borrowBookUseCase() {
		return context.getBorrowBookUseCase();
	}

	@Bean
	public GetUser getUserUseCase() {
		return context.getGetUserUseCase();
	}

	@Bean
	public GetAuthor getAuthorUseCase() {
		return context.getGetAuthorUseCase();
	}

	@Bean
	public GetBook getBookUseCase() {
		return context.getGetBookUseCase();
	}

	@Bean
	public GetBookContent getBookContentUseCase() {
		return context.getGetBookContentUseCase();
	}

	@Bean
	public GetBookReviews getBookReviewsUseCase() {
		return context.getGetBookReviewsUseCase();
	}

	@Bean
	public GetCart getCartUseCase() {
		return context.getGetCartUseCase();
	}

	@Bean
	public GetPurchaseHistory getPurchaseHistoryUseCase() {
		return context.getGetPurchaseHistoryUseCase();
	}

	@Bean
	public GetPurchasedBooks getPurchasedBooksUseCase() {
		return context.getGetPurchasedBooksUseCase();
	}

	@Bean
	public AddReview addReviewUseCase() {
		return context.getAddReviewUseCase();
	}

	@Bean
	public Login loginUseCase() {
		return context.getLoginUseCase();
	}
}
