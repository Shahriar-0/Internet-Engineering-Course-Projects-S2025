package application.usecase.admin;

import application.exceptions.businessexceptions.authorexceptions.AuthorDoesNotExists;
import application.exceptions.businessexceptions.bookexceptions.BookAlreadyExists;
import application.exceptions.businessexceptions.userexceptions.InvalidAccess;
import application.repositories.IAuthorRepository;
import application.repositories.IBookRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.Book;
import domain.entities.User;
import domain.valueobjects.BookContent;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class AddBookUseCase implements IUseCase {

	private final IAuthorRepository authorRepository;
	private final IBookRepository bookRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.ADD_BOOK;
	}

	public Result<Book> perform(AddBookData data, User.Role role) {
		if (!User.Role.ADMIN.equals(role))
			return Result.failure(new InvalidAccess("admin"));

		if (!authorRepository.exists(data.author))
			return Result.failure(new AuthorDoesNotExists(data.author));

		if (bookRepository.exists(data.title))
			return Result.failure(new BookAlreadyExists(data.title));

		return bookRepository.add(mapToBook(data));
	}

	private Book mapToBook(AddBookData data) {
		return Book
			.builder()
			.key(data.title)
			.author(authorRepository.get(data.author).getData())
			.publisher(data.publisher)
			.year(data.year)
			.price(data.price)
			.synopsis(data.synopsis)
			.content(new BookContent(data.content, data.title))
			.genres(data.genres)
			.build();
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
