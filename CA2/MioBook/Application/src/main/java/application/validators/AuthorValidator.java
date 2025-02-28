package application.validators;

import application.dtos.AddAuthorDto;
import application.exceptions.businessexceptions.authorexceptions.AuthorAlreadyExists;
import application.exceptions.businessexceptions.authorexceptions.AuthorDoesNotExists;
import application.repositories.IAuthorRepository;
import application.result.Result;
import domain.entities.Author;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthorValidator implements IBaseValidator<AddAuthorDto> {

	private final IAuthorRepository authorRepository;

	@Override
	public Result<AddAuthorDto> validate(AddAuthorDto authorDto) {
		if (authorRepository.exists(authorDto.name()))
			return Result.failureOf(new AuthorAlreadyExists(authorDto.name()));

		return Result.successOf(authorDto);
	}

	public Result<Author> getAuthor(String authorName) { // FIXME: this shouldn't be here but I don't know what to for now
		if (!authorRepository.exists(authorName))
            return Result.failureOf(new AuthorDoesNotExists(authorName));

        return Result.successOf(authorRepository.find(authorName).getData());
	}
}
