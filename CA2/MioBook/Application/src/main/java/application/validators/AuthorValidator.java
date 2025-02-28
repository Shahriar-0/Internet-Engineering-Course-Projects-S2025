package application.validators;

import application.exceptions.businessexceptions.authorexceptions.AuthorAlreadyExists;
import application.exceptions.businessexceptions.authorexceptions.AuthorDoesNotExists;
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

	public Result<Author> getAuthor(String authorName) { // FIXME: this shouldn't be here but I don't know what to for now
		if (!authorRepository.exists(authorName))
            return Result.failureOf(new AuthorDoesNotExists(authorName));

        return Result.successOf(authorRepository.find(authorName).getData());
	}
}
