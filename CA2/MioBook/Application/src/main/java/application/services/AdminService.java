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
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdminService {

	private final AuthorValidator authorValidator;
	private final IAuthorRepository authorRepository;
	private final BookValidator BookValidator;
	private final IBookRepository bookRepository;
	private final UserService userService;

	/**
	 * Adds a new author to the system.
	 *
	 * @param newAuthorDto The new author to add, as a DTO.
	 * @return A Result indicating whether the operation was successful. If the operation was
	 *         unsuccessful, the contained exception will be a subclass of
	 *         {@link application.exceptions.businessexceptions.BusinessException}.
	 */
	public Result<Author> addAuthor(AddAuthorDto newAuthorDto) {
		Result<User> isAdminResult = isAdmin(newAuthorDto.username());
		if (isAdminResult.isFailure())
			return new Result<>(isAdminResult);

		Result<AddAuthorDto> validationResult = authorValidator.validate(newAuthorDto);
		if (validationResult.isFailure())
			return new Result<>(validationResult);

		Author newAuthor = UtilService.createAuthor(newAuthorDto);
		return authorRepository.add(newAuthor);
	}

	/**
	 * Adds a new book to the system.
	 *
	 * @param newBookDto The new book to add, as a DTO.
	 * @return A Result indicating whether the operation was successful. If the operation was
	 *         unsuccessful, the contained exception will be a subclass of
	 *         {@link application.exceptions.businessexceptions.BusinessException}.
	 */
	public Result<Book> addBook(AddBookDto newBookDto) {
		Result<User> isAdminResult = isAdmin(newBookDto.username());
		if (isAdminResult.isFailure())
			return new Result<>(isAdminResult);

		Result<AddBookDto> validationResult = BookValidator.validate(newBookDto);
		if (validationResult.isFailure())
			return new Result<>(validationResult);

		Book newBook = UtilService.createBook(newBookDto, authorRepository);
		return bookRepository.add(newBook);
	}

	private Result<User> isAdmin(String username) {
		Result<User> userSearchResult = userService.doesExist(username);
		if (userSearchResult.isFailure())
			return new Result<>(userSearchResult);
		else if (userSearchResult.getData().getRole() != User.Role.ADMIN)
			return Result.failure(new InvalidAccess("admin"));

		return userSearchResult;
	}
}
