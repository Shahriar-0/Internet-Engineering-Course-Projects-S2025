package application.usecase.customer.book;

import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.Customer;
import domain.entities.User;
import domain.valueobjects.PurchasedBooks;

public class GetPurchasedBooks implements IUseCase {
    @Override
    public UseCaseType getType() {
        return UseCaseType.GET_PURCHASED_BOOKS;
    }

    public Result<PurchasedBooks> perform(User user) {
        assert user instanceof Customer: "we rely on presentation layer access control";
        Customer customer = (Customer) user;

        return Result.success(customer.getPurchasedBooks());
    }
}
