package application.usecase.customer;

import application.exceptions.businessexceptions.userexceptions.BookIsNotAccessible;
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
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class GetBookContentUseCase implements IUseCase {

	private final IUserRepository userRepository;
	private final IBookRepository bookRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.GET_BOOK_CONTENT;
	}

	public Result<BookContent> perform(GetBookContentData data, String username) {
		Result<User> userResult = userRepository.get(username);
		if (userResult.isFailure())
            return Result.failure(userResult.getException());
		assert userResult.getData() instanceof Customer : "we relay on role passing from presentation layer";
		Customer customer = (Customer) userResult.getData();

		Result<Book> bookResult = bookRepository.get(data.title);
		if (bookResult.isFailure())
            return Result.failure(bookResult.getException());

		if (!customer.hasBought(bookResult.getData()))
            return Result.failure(new BookIsNotAccessible(data.title));

		return Result.success(bookResult.getData().getContent());
	}

	public record GetBookContentData(@NotBlank String title) {}
}
