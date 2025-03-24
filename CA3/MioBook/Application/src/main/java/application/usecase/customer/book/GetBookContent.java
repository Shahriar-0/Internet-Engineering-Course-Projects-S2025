package application.usecase.customer.book;

import application.exceptions.businessexceptions.userexceptions.BookIsNotAccessible;
import application.repositories.IBookRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.book.Book;
import domain.entities.user.Customer;
import domain.entities.user.User;
import domain.entities.book.BookContent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetBookContent implements IUseCase {

	private final IBookRepository bookRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.GET_BOOK_CONTENT;
	}

	public Result<BookContent> perform(String title, User user) {
		assert user instanceof Customer: "we rely on presentation layer access control";
		Customer customer = (Customer) user;

		Result<Book> bookResult = bookRepository.get(title);
		if (bookResult.isFailure())
            return Result.failure(bookResult.exception());

		if (!customer.hasAccess(bookResult.data()))
            return Result.failure(new BookIsNotAccessible(title));

		return Result.success(bookResult.data().getContent());
	}
}
