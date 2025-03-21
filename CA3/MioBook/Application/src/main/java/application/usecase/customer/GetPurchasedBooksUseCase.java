package application.usecase.customer;

import application.exceptions.businessexceptions.userexceptions.InvalidAccess;
import application.repositories.IUserRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.Customer;
import domain.entities.User;
import domain.valueobjects.PurchasedBooks;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetPurchasedBooksUseCase implements IUseCase {

    private final IUserRepository userRepository;

    @Override
    public UseCaseType getType() {
        return UseCaseType.GET_PURCHASED_BOOKS;
    }

    public Result<PurchasedBooks> perform(String username, User.Role role) {
        if (!User.Role.CUSTOMER.equals(role))
            return Result.failure(new InvalidAccess("customer"));
        Result<User> userResult = userRepository.get(username);
        if (userResult.isFailure())
            return Result.failure(userResult.exception());
        assert userResult.data() instanceof Customer : "we relay on role passing from presentation layer";
        Customer customer = (Customer) userResult.data();
        return Result.success(customer.getPurchasedBooks());
    }
}
