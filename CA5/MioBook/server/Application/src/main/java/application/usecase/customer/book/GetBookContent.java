package application.usecase.customer.book;

import application.exceptions.businessexceptions.bookexceptions.BookDoesntExist;
import application.exceptions.businessexceptions.userexceptions.BookIsNotAccessible;
import application.repositories.IBookRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.book.Book;
import domain.entities.book.BookContent;
import domain.entities.user.Customer;
import domain.entities.user.User;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
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

		Optional<Book> bookResult = bookRepository.findByTitle(title);
		if (bookResult.isEmpty())
			return Result.failure(new BookDoesntExist(title));
		Book book = bookResult.get();

		if (!customer.hasAccess(title))
           return Result.failure(new BookIsNotAccessible(title));

		return Result.success(book.getContent());
	}
}
