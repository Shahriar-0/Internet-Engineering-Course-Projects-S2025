package webapi.configuration;

import application.repositories.*;
import application.usecase.admin.*;
import application.usecase.customer.*;
import application.usecase.user.*;

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
	public AddUserUseCase addUserUseCase() {
		return context.getAddUserUseCase();
	}

	@Bean
	public AddAuthorUseCase addAuthorUseCase() {
		return context.getAddAuthorUseCase();
	}

	@Bean
	public AddBookUseCase addBookUseCase() {
		return context.getAddBookUseCase();
	}

	@Bean
	public AddCartUseCase addCartUseCase() {
		return context.getAddCartUseCase();
	}

	@Bean
	public RemoveCartUseCase removeCartUseCase() {
		return context.getRemoveCartUseCase();
	}

	@Bean
	public AddCreditUseCase addCreditUseCase() {
		return context.getAddCreditUseCase();
	}

	@Bean
	public PurchaseCartUseCase purchaseCartUseCase() {
		return context.getPurchaseCartUseCase();
	}

	@Bean
	public BorrowBookUseCase borrowBookUseCase() {
		return context.getBorrowBookUseCase();
	}

	@Bean
	public GetUserUseCase getUserUseCase() {
		return context.getGetUserUseCase();
	}

	@Bean
	public GetAuthorUseCase getAuthorUseCase() {
		return context.getGetAuthorUseCase();
	}

	@Bean
	public GetBookUseCase getBookUseCase() {
		return context.getGetBookUseCase();
	}

	@Bean
	public GetBookContentUseCase getBookContentUseCase() {
		return context.getGetBookContentUseCase();
	}

	@Bean
	public GetBookReviewsUseCase getBookReviewsUseCase() {
		return context.getGetBookReviewsUseCase();
	}

	@Bean
	public GetCartUseCase getCartUseCase() {
		return context.getGetCartUseCase();
	}

	@Bean
	public GetPurchaseHistoryUseCase getPurchaseHistoryUseCase() {
		return context.getGetPurchaseHistoryUseCase();
	}

	@Bean
	public GetPurchasedBooksUseCase getPurchasedBooksUseCase() {
		return context.getGetPurchasedBooksUseCase();
	}

	@Bean
	public AddReviewUseCase addReviewUseCase() {
		return context.getAddReviewUseCase();
	}
}
