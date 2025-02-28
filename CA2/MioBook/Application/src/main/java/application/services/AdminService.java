package application.services;

import java.time.LocalDate;

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
import domain.valueobjects.BookContent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdminService {

	private final AuthorValidator authorValidator;
	private final IAuthorRepository authorRepository;
	private final BookValidator BookValidator;
	private final IBookRepository bookRepository;
	private final UserService userService;

	public Result<Author> addAuthor(AddAuthorDto newAuthorDto) {
		Result<Boolean> isAdminResult = userService.isAdmin(newAuthorDto.username());
		if (isAdminResult.isFailure()) // TODO: imporve readability here, it's kinda misleading
			return Result.failureOf(isAdminResult.getException());
		else if (!isAdminResult.getData())
			return Result.failureOf(new InvalidAccess());

		Result<AddAuthorDto> validationResult = authorValidator.validate(newAuthorDto);
		if (validationResult.isFailure())
			return Result.failureOf(validationResult.getException());

		Author newAuthor = createAuthor(newAuthorDto);
		return authorRepository.add(newAuthor);
	}

	public Result<Book> addBook(AddBookDto newBookDto) {
		Result<Boolean> isAdminResult = userService.isAdmin(newBookDto.username());
		if (isAdminResult.isFailure())
			return Result.failureOf(isAdminResult.getException()); // TODO: find better way for converting exceptions
		else if (!isAdminResult.getData())
			return Result.failureOf(new InvalidAccess());

		Result<AddBookDto> validationResult = BookValidator.validate(newBookDto);
		if (validationResult.isFailure())
			return Result.failureOf(validationResult.getException());

		Book newBook = createBook(newBookDto);
		return bookRepository.add(newBook);
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
        return Book.builder()
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
