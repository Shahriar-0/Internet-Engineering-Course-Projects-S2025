package webapi.configuration;

import application.services.AdminService;
import application.uscase.admin.AddBookUseCase;
import application.uscase.customer.AddCartUseCase;
import application.uscase.customer.AddCreditUseCase;
import application.uscase.customer.PurchaseCartUseCase;
import application.uscase.customer.RemoveCartUseCase;
import application.uscase.user.AddUserUseCase;
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
}
