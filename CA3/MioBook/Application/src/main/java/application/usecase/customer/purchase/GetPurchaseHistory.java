package application.usecase.customer.purchase;

import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.Customer;
import domain.entities.User;
import domain.valueobjects.PurchaseHistory;

public class GetPurchaseHistory implements IUseCase {
    @Override
    public UseCaseType getType() {
        return UseCaseType.GET_PURCHASE_HISTORY;
    }

    public Result<PurchaseHistory> perform(User user) {
        assert user instanceof Customer: "we relay on presentation layer access control";
        Customer customer = (Customer) user;

        return Result.success(customer.getPurchaseHistory());
    }
}
