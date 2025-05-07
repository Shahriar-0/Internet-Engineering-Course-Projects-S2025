package application.usecase.admin.book;

import application.exceptions.businessexceptions.authorexceptions.AuthorDoesNotExists;
import application.exceptions.businessexceptions.bookexceptions.BookAlreadyExists;
import application.repositories.IAuthorRepository;
import application.repositories.IBookRepository;
import application.result.Result;
import application.usecase.IUseCase;
import application.usecase.UseCaseType;
import domain.entities.author.Author;
import domain.entities.book.Book;
import domain.entities.book.BookContent;
import domain.entities.user.Admin;
import domain.entities.user.Role;
import domain.entities.user.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AddBook implements IUseCase {

	private final IAuthorRepository authorRepository;
	private final IBookRepository bookRepository;

	@Override
	public UseCaseType getType() {
		return UseCaseType.ADD_BOOK;
	}

	public Result<Book> perform(AddBookData data, User user) {
		assert Role.ADMIN.equals(user.getRole()) : "we rely on presentation layer access control";

		Optional<Author> authorResult = authorRepository.findByName(data.author);
		if (authorResult.isEmpty())
			return Result.failure(new AuthorDoesNotExists(data.author));
		Author author = authorResult.get();

		Optional<Book> bookResult = bookRepository.findByTitle(data.title);
		if (bookResult.isPresent())
			return Result.failure(new BookAlreadyExists(data.title));

		Book book = mapToBook(data, author, (Admin) user);
		author.addBook(book);
 		return Result.success(bookRepository.save(book));
	}

	public static Book mapToBook(AddBookData data, Author author, Admin admin) {
        return Book.builder()
				.title(data.title)
				.publisher(data.publisher)
				.publishedYear(data.year)
				.basePrice(data.price)
				.synopsis(data.synopsis)
				.imageLink(data.imageLink)
				.dateAdded(LocalDateTime.now())
				.genres(data.genres)
				.content(BookContent.builder().content(data.content).build())
				.admin(admin)
				.author(author)
				.build();
	}

	public record AddBookData(
		@NotBlank String author,
		@NotBlank String title,
		@NotBlank String publisher,
		@NotBlank String synopsis,
		@NotBlank String content,
		@NotNull @Positive Integer year,
		@NotNull @Positive Long price,
		@NotEmpty @Valid List<@NotBlank String> genres,
        String imageLink
	) {}
}
