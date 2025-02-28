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

	@Override
	public Result<AddBookDto> validate(AddBookDto input) {
        Result<Author> authorResult = authorValidator.getAuthor(input.author());
        if (authorResult.isFailure())
            return Result.failureOf(new AuthorDoesNotExists(input.author()));

        if (bookRepository.exists(input.title()))
            return Result.failureOf(new BookAlreadyExists(input.title()));

        return Result.successOf(input);
	}
}
