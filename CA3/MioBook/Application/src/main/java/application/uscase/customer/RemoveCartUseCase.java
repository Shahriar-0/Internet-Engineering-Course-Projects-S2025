package application.uscase.customer;

import application.dtos.RemoveCartDto;
import application.exceptions.businessexceptions.cartexceptions.CantRemoveFromCart;
import application.exceptions.businessexceptions.userexceptions.InvalidAccess;
import application.repositories.IBookRepository;
import application.repositories.IUserRepository;
import application.result.Result;
import application.uscase.IUseCase;
import application.uscase.UseCaseType;
import domain.entities.Book;
import domain.entities.Customer;
import domain.entities.User;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RemoveCartUseCase implements IUseCase {
    private final IUserRepository userRepository;
    private final IBookRepository bookRepository;

    @Override
    public UseCaseType getType() {
        return UseCaseType.REMOVE_CART;
    }

    public Result<Customer> perform(RemoveCartData data, String userName, User.Role role) {
        if (User.Role.CUSTOMER.equals(role))
            return Result.failure(new InvalidAccess("customer"));

        Result<User> userResult = userRepository.get(userName);
        if (userResult.isFailure())
            return Result.failure(userResult.getException());
        assert userResult.getData() instanceof Customer: "we relay on role passing from presentation layer";
        Customer customer = (Customer) userResult.getData();

        Result<Book> bookResult = bookRepository.get(data.title);
        if (bookResult.isFailure())
            return Result.failure(bookResult.getException());
        Book book = bookResult.getData();

        if (!customer.canRemoveBook(book))
            return Result.failure(new CantRemoveFromCart(customer.findRemoveBookErrors(book)));

        customer.removeBook(book);
        return Result.success(customer);
    }

    public record RemoveCartData (
            @NotBlank(message = "Title is required")
            String title
    ) {}
}
