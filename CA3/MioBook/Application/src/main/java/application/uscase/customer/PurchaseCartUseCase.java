package application.uscase.customer;

import application.exceptions.businessexceptions.cartexceptions.CantPurchaseCart;
import application.exceptions.businessexceptions.userexceptions.InvalidAccess;
import application.repositories.IUserRepository;
import application.result.Result;
import application.uscase.IUseCase;
import application.uscase.UseCaseType;
import domain.entities.Customer;
import domain.entities.User;
import domain.valueobjects.PurchasedCart;
import domain.valueobjects.PurchasedCartSummary;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PurchaseCartUseCase implements IUseCase {
    private final IUserRepository userRepository;

    @Override
    public UseCaseType getType() {
        return UseCaseType.PURCHASE_CART;
    }

    public Result<PurchasedCartSummary> perform(String userName, User.Role role) {
        if (!User.Role.CUSTOMER.equals(role))
            return Result.failure(new InvalidAccess("customer"));

        Result<User> userResult = userRepository.get(userName);
        if (userResult.isFailure())
            return Result.failure(userResult.getException());
        assert userResult.getData() instanceof Customer : "we relay on role passing from presentation layer";
        Customer customer = (Customer) userResult.getData();

        if (!customer.canPurchaseCart())
            return Result.failure(new CantPurchaseCart(customer.findPurchaseCartErrors()));

        PurchasedCart purchasedCart = customer.purchaseCart();
        return Result.success(new PurchasedCartSummary(purchasedCart));
    }
}
