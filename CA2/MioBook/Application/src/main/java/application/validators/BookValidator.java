package application.validators;

import application.dtos.AddBookDto;
import application.exceptions.businessexceptions.authorexceptions.AuthorDoesNotExists;
import application.exceptions.businessexceptions.bookexceptions.BookAlreadyExists;
import application.repositories.IBookRepository;
import application.result.Result;
import domain.entities.Author;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BookValidator implements IBaseValidator<AddBookDto> {

	private final IBookRepository bookRepository;
	private final AuthorValidator authorValidator;

	/**
	 * Validates a new book. It checks if the author exists and if a book with the same title already exists.
	 *
	 * @param input The new book to add, as a DTO.
	 * @return A Result indicating whether the operation was successful. If the operation was
	 *         unsuccessful, the contained exception will be a subclass of
	 *         {@link application.exceptions.businessexceptions.BusinessException}. The only
	 *         possible exceptions are an {@link application.exceptions.businessexceptions.authorexceptions.AuthorDoesNotExists} if the author does not exist, or a
	 *         {@link application.exceptions.businessexceptions.bookexceptions.BookAlreadyExists} if a book with the same title already exists.
	 */
	@Override
	public Result<AddBookDto> validate(AddBookDto input) {
		Result<Author> authorResult = authorValidator.getAuthor(input.author());
		if (authorResult.isFailure())
            return Result.failure(new AuthorDoesNotExists(input.author()));

		if (bookRepository.exists(input.title()))
            return Result.failure(new BookAlreadyExists(input.title()));

		return Result.success(input);
	}
}
