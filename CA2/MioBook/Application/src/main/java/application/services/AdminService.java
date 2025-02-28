package application.services;

import application.dtos.AddAuthorDto;
import application.dtos.AddBookDto;
import application.exceptions.businessexceptions.userexceptions.InvalidAccess;
import application.repositories.IAuthorRepository;
import application.repositories.IBookRepository;
import application.result.Result;
import application.validators.AuthorValidator;
import application.validators.BookValidator;
import domain.entities.Author;
import domain.entities.Book;
import domain.entities.User;
import domain.valueobjects.BookContent;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdminService {

	private final AuthorValidator authorValidator;
	private final IAuthorRepository authorRepository;
	private final BookValidator BookValidator;
	private final IBookRepository bookRepository;
	private final UserService userService;

	public Result<Author> addAuthor(AddAuthorDto newAuthorDto) {
		Result<User> isAdminResult = isAdmin(newAuthorDto.username());
		if (isAdminResult.isFailure())
			return new Result<>(isAdminResult);

		Result<AddAuthorDto> validationResult = authorValidator.validate(newAuthorDto);
		if (validationResult.isFailure())
			return new Result<>(validationResult);

		Author newAuthor = createAuthor(newAuthorDto);
		return authorRepository.add(newAuthor);
	}

	public Result<Book> addBook(AddBookDto newBookDto) {
		Result<User> isAdminResult = isAdmin(newBookDto.username());
		if (isAdminResult.isFailure())
			return new Result<>(isAdminResult);

		Result<AddBookDto> validationResult = BookValidator.validate(newBookDto);
		if (validationResult.isFailure())
			return new Result<>(validationResult);

		Book newBook = createBook(newBookDto);
		return bookRepository.add(newBook);
	}

	private Result<User> isAdmin(String username) {
		Result<User> userSearchResult = userService.doesExist(username);
		if (userSearchResult.isFailure())
			return new Result<>(userSearchResult);
		else if (userSearchResult.getData().getRole() != User.Role.ADMIN)
			return Result.failure(new InvalidAccess());

		return userSearchResult;
	}

	private Author createAuthor(AddAuthorDto dto) {
		return Author
			.builder()
			.key(dto.name())
			.penName(dto.penName())
			.nationality(dto.nationality())
			.born(LocalDate.parse(dto.born()))
			.died(dto.died() == null ? null : LocalDate.parse(dto.died()))
			.build();
	}

	private Book createBook(AddBookDto dto) {
		return Book
			.builder()
			.key(dto.title())
			.author(authorRepository.find(dto.author()).getData())
			.publisher(dto.publisher())
			.year(dto.year())
			.price(dto.price())
			.synopsis(dto.synopsis())
			.content(new BookContent(dto.content()))
			.genres(dto.genres())
			.build();
	}
}
