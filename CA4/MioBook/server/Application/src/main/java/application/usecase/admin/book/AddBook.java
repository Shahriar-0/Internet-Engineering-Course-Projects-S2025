package application.usecase.admin.book;

import application.exceptions.businessexceptions.authorexceptions.AuthorDoesNotExists;
import application.exceptions.businessexceptions.bookexceptions.BookAlreadyExists;
import application.repositories.IAuthorRepository;
import application.repositories.IBookRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.Author;
import domain.entities.book.Book;
import domain.entities.user.Role;
import domain.entities.user.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AddBook implements IUseCase {

	private final IAuthorRepository authorRepository;
	private final IBookRepository bookRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.ADD_BOOK;
	}

	public Result<Book> perform(AddBookData data, User user) {
		assert Role.ADMIN.equals(user.getRole()): "we rely on presentation layer access control";

		Result<Author> authorResult = authorRepository.get(data.author);
		if (authorResult.isFailure())
			return Result.failure(new AuthorDoesNotExists(data.author));
		Author author = authorResult.data();

		if (bookRepository.exists(data.title))
			return Result.failure(new BookAlreadyExists(data.title));


		Book book = mapToBook(data, author);
		author.addBook(book);
		return bookRepository.add(book);
	}

	private static Book mapToBook(AddBookData data, Author author) {
		return new Book(
            data.title,
            author,
            data.publisher,
            data.year,
            data.price,
            data.synopsis,
            data.genres,
            data.content
        );
	}

	public record AddBookData(
		@NotBlank String author,
		@NotBlank String title,
		@NotBlank String publisher,
		@NotBlank String synopsis,
		@NotBlank String content,

		@NotNull
		@Positive
		Integer year,

		@NotNull
		@Positive
		Long price,

		@NotEmpty
		@Valid
		List<@NotBlank String> genres
	) {}
}
