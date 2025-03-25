package application.usecase.customer.book;

import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.booklicense.BookLicense;
import domain.entities.user.Customer;
import domain.entities.user.User;

import java.util.List;

public class GetPurchasedBooks implements IUseCase {
    @Override
    public UseCaseType getType() {
        return UseCaseType.GET_PURCHASED_BOOKS;
    }

    public Result<List<BookLicense>> perform(User user) {
        assert user instanceof Customer: "we rely on presentation layer access control";
        Customer customer = (Customer) user;

        return Result.success(customer.getValidLicenses());
    }
}
