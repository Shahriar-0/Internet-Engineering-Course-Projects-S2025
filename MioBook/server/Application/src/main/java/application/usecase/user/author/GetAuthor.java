package application.usecase.user.author;

import application.exceptions.businessexceptions.authorexceptions.AuthorDoesNotExists;
import application.repositories.IAuthorRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.author.Author;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetAuthor implements IUseCase {

	private static final int MAX_AUTHOR_PAGE_SIZE = 100;
	private static final int DEFAULT_AUTHOR_PAGE_SIZE = 20;
	private static final int DEFAULT_AUTHOR_PAGE_NUMBER = 1;

	private final IAuthorRepository authorRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.GET_AUTHOR;
	}

	public Result<Author> perform(String name) {
		assert name != null && !name.isBlank() : "we rely on presentation layer validation for field 'name'";

        Optional<Author> author = authorRepository.findByName(name);
        return author.map(Result::success).orElseGet(() -> Result.failure(new AuthorDoesNotExists(name)));
    }

	public List<Author> perform() {
		return authorRepository.findAllWithBooks();
    }

	public Page<Author> perform(AuthorFilter filter) {
		AuthorFilter standardFilter = standardizeFilter(filter);
		return authorRepository.filter(standardFilter);
	}

	private static AuthorFilter standardizeFilter(AuthorFilter filter) {
		return new AuthorFilter(
			filter.name(),
			filter.nationality(),
			filter.admin(),
			(filter.pageNumber() != null) ? filter.pageNumber() : DEFAULT_AUTHOR_PAGE_NUMBER,
			standardizePageSizeField(filter.pageSize())
		);
	}

	private static int standardizePageSizeField(Integer currentPageSize) {
		if (currentPageSize == null)
			return DEFAULT_AUTHOR_PAGE_SIZE;

		if (currentPageSize > MAX_AUTHOR_PAGE_SIZE)
			return MAX_AUTHOR_PAGE_SIZE;

		return currentPageSize;
	}

	public record AuthorFilter(
		String name,
		String nationality,
		String admin,
		Integer pageNumber,
		Integer pageSize
	) {}
}
