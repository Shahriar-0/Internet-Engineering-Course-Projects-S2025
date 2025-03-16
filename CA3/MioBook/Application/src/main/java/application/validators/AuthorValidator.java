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

	/**
	 * Checks if an author with the given username already exists in the system.
	 *
	 * @param authorDto The new author to add, as a DTO.
	 * @return A Result indicating whether the operation was successful. If the operation was
	 *         unsuccessful, the contained exception will be a subclass of
	 *         {@link application.exceptions.businessexceptions.authorexceptions.AuthorAlreadyExists}.
	 */
	@Override
	public Result<AddAuthorDto> validate(AddAuthorDto authorDto) {
		if (authorRepository.exists(authorDto.name()))
			return Result.failure(new AuthorAlreadyExists(authorDto.name()));

		return Result.success(authorDto);
	}

	public Result<Author> getAuthor(String authorName) {
		if (!authorRepository.exists(authorName))
			return Result.failure(new AuthorDoesNotExists(authorName));

		return Result.success(authorRepository.find(authorName).getData());
	}
}
