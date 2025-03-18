package application.usecase.user;

import application.repositories.IAuthorRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.Author;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetAuthorUseCase implements IUseCase {

	private final IAuthorRepository authorRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.GET_AUTHOR;
	}

	public Result<Author> perform(String name) {
		assert name != null && !name.isBlank() : "we relay on @NotBlank validation on name field in presentation layer";

		return authorRepository.find(name);
	}

	// TODO: this feature added soon
	public Result<List<Author>> perform(AuthorFilter filter) {
		throw new RuntimeException("Not implemented yet");
	}

	// TODO: this feature added soon
	public record AuthorFilter() {}
}
