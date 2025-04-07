package application.usecase.customer.purchase;

import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.cart.PurchasedCart;
import domain.entities.user.Customer;
import domain.entities.user.User;

import java.util.List;

public class GetPurchaseHistory implements IUseCase {
    @Override
    public UseCaseType getType() {
        return UseCaseType.GET_PURCHASE_HISTORY;
    }

    public Result<List<PurchasedCart>> perform(User user) {
        assert user instanceof Customer: "we rely on presentation layer access control";
        Customer customer = (Customer) user;

        return Result.success(customer.getPurchaseHistory());
    }
}
