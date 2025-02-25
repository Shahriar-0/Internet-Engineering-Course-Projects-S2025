package application.validators;

import application.exceptions.businessexceptions.authorexceptions.AuthorAlreadyExists;
import application.repositories.IAuthorRepository;
import application.result.Result;
import domain.entities.Author;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthorValidator implements IBaseValidator<Author> {

	private final IAuthorRepository authorRepository;

	@Override
	public Result<Author> validate(Author author) {
		if (authorRepository.exists(author.getKey()))
			return Result.failureOf(new AuthorAlreadyExists(author.getName()));

		return Result.successOf(author);
	}
}
