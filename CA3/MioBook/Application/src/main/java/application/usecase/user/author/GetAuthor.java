package application.usecase.user.author;

import application.repositories.IAuthorRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.Author;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class GetAuthor implements IUseCase {

	private final IAuthorRepository authorRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.GET_AUTHOR;
	}

	public Result<Author> perform(String name) {
		assert name != null && !name.isBlank() : "we rely on presentation layer validation for field 'name'";

		return authorRepository.find(name);
	}

	public Result<List<Author>> perform(AuthorFilter filter) {
		throw new RuntimeException("Not implemented yet");
	}

	public record AuthorFilter() {}
}
