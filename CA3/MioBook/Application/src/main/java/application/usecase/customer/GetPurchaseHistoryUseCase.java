package application.usecase.customer;

import application.repositories.IUserRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.Customer;
import domain.entities.User;
import domain.valueobjects.PurchaseHistory;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetPurchaseHistoryUseCase implements IUseCase {

    private final IUserRepository userRepository;

    @Override
    public UseCaseType getType() {
        return UseCaseType.GET_PURCHASE_HISTORY;
    }

    public Result<PurchaseHistory> perform(String username) {
        Result<User> userResult = userRepository.get(username);
        if (userResult.isFailure())
            return Result.failure(userResult.getException());
        assert userResult.getData() instanceof Customer : "we relay on role passing from presentation layer";
        Customer customer = (Customer) userResult.getData();
        return Result.success(customer.getPurchaseHistory());
    }
}
