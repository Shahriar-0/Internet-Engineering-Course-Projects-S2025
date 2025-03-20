package application.usecase.customer;

import application.exceptions.businessexceptions.userexceptions.BookIsNotAccessible;
import application.exceptions.businessexceptions.userexceptions.InvalidAccess;
import application.repositories.IBookRepository;
import application.repositories.IUserRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.Book;
import domain.entities.Customer;
import domain.entities.User;
import domain.valueobjects.BookContent;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetBookContentUseCase implements IUseCase {

	private final IUserRepository userRepository;
	private final IBookRepository bookRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.GET_BOOK_CONTENT;
	}

	public Result<BookContent> perform(String title, String username, User.Role role) {
		if (!User.Role.CUSTOMER.equals(role))
            return Result.failure(new InvalidAccess("customer"));

		Result<User> userResult = userRepository.get(username);
		if (userResult.isFailure())
            return Result.failure(userResult.getException());
		assert userResult.getData() instanceof Customer : "we relay on role passing from presentation layer";
		Customer customer = (Customer) userResult.getData();

		Result<Book> bookResult = bookRepository.get(title);
		if (bookResult.isFailure())
            return Result.failure(bookResult.getException());

		if (!customer.hasBought(bookResult.getData()))
            return Result.failure(new BookIsNotAccessible(title));

		return Result.success(bookResult.getData().getContent());
	}
}
