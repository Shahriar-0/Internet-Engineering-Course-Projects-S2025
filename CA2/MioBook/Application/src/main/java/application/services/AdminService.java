package application.services;

import application.exceptions.businessexceptions.userexceptions.InvalidAccess;
import application.repositories.IAuthorRepository;
import application.repositories.IBookRepository;
import application.result.Result;
import application.validators.AuthorValidator;
import application.validators.BookValidator;
import domain.entities.Author;
import domain.entities.Book;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AdminService {

	private final AuthorValidator authorValidator;
	private final IAuthorRepository authorRepository;
	private final BookValidator BookValidator;
	private final IBookRepository bookRepository;
	private final UserService userService;

	public Result<Author> addAuthor(Author newAuthor, String adminUsername) {
		Result<Boolean> isAdminResult = userService.isAdmin(adminUsername);
		if (isAdminResult.isFailure()) // TODO: imporve readability here, it's kinda misleading
			return Result.failureOf(isAdminResult.getException());
		else if (!isAdminResult.getData())
			return Result.failureOf(new InvalidAccess());

		Result<Author> validationResult = authorValidator.validate(newAuthor);
		if (validationResult.isFailure())
			return validationResult;

		return authorRepository.add(newAuthor);
	}

	public Result<Book> addBook(Book newBook, String adminUsername) {
		Result<Boolean> isAdminResult = userService.isAdmin(adminUsername);
		if (isAdminResult.isFailure())
			return Result.failureOf(isAdminResult.getException()); // TODO: find better way for converting exceptions
		else if (!isAdminResult.getData())
			return Result.failureOf(new InvalidAccess());

		Result<Book> validationResult = BookValidator.validate(newBook);
		if (validationResult.isFailure())
			return validationResult;

		return bookRepository.add(newBook);
	}
}
