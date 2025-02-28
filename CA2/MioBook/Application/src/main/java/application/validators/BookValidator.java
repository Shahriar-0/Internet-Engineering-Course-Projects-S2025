package application.validators;

import application.exceptions.businessexceptions.authorexceptions.AuthorDoesNotExists;
import application.exceptions.businessexceptions.bookexceptions.BookAlreadyExists;
import application.repositories.IBookRepository;
import application.result.Result;
import domain.entities.Author;
import domain.entities.Book;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookValidator implements IBaseValidator<Book> {

    private final IBookRepository bookRepository;
    private final AuthorValidator authorValidator;

	@Override
	public Result<Book> validate(Book input) {
        Result<Author> authorResult = authorValidator.getAuthor(input.getAuthorString());
        if (authorResult.isFailure())
            return Result.failureOf(new AuthorDoesNotExists(input.getAuthorString()));

        if (bookRepository.exists(input.getTitle()))
            return Result.failureOf(new BookAlreadyExists(input.getTitle()));

        return Result.successOf(input);
	}
}
