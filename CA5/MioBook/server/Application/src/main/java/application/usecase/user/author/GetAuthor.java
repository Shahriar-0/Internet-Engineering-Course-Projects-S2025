package application.usecase.user.author;

import application.exceptions.businessexceptions.authorexceptions.AuthorDoesNotExists;
import application.repositories.IAuthorRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.author.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetAuthor implements IUseCase {

	private final IAuthorRepository authorRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.GET_AUTHOR;
	}

	public Result<Author> perform(String name) {
		assert name != null && !name.isBlank() : "we rely on presentation layer validation for field 'name'";

        Optional<Author> author = authorRepository.findByName(name);
        return author.map(Result::success)
            .orElseGet(() -> Result.failure(new AuthorDoesNotExists(name)));
    }

	public List<Author> perform() {
		return authorRepository.findAllWithBooks();
    }

	public Result<List<Author>> perform(AuthorFilter filter) {
		throw new RuntimeException("Not implemented yet");
	}

	public record AuthorFilter() {}
}
