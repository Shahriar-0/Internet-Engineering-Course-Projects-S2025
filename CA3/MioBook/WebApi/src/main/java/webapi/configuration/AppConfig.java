package webapi.configuration;

import application.services.AdminService;
import application.uscase.admin.AddBookUseCase;
import application.uscase.customer.*;
import application.uscase.user.AddUserUseCase;
import application.uscase.user.GetAuthorUseCase;
import application.uscase.user.GetBookUseCase;
import application.uscase.user.GetUserUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

	private final AppContext context = new AppContext();

	@Bean
	public AdminService adminService() {
		return context.getAdminService();
	}

	@Bean
	public AddUserUseCase addUserUseCase() {
		return context.getAddUserUseCase();
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
}
