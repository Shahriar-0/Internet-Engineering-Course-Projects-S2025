package application.services;

import application.dtos.*;
import application.exceptions.businessexceptions.cartexceptions.*;
import application.exceptions.businessexceptions.userexceptions.*;
import application.repositories.IBookRepository;
import application.repositories.IUserRepository;
import application.result.Result;
import application.validators.UserValidator;
import domain.entities.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {

	private final IUserRepository userRepo;
	private final UserValidator userValidator;
	private final IBookRepository bookRepo;

	/**
	 * Adds a new user to the system.
	 *
	 * @param newUserDto The new user to add, as a DTO.
	 * @return A Result indicating whether the operation was successful. If the operation was
	 *         unsuccessful, the contained exception will be a subclass of
	 *         {@link application.exceptions.businessexceptions.BusinessException}.
	 */
	public Result<User> addUser(AddUserDto newUserDto) {
		Result<AddUserDto> validationResult = userValidator.validate(newUserDto);
		if (validationResult.isFailure())
			return new Result<>(validationResult);

		User newUser = UtilService.createUser(newUserDto);
		newUser.setCredit(0);
		return userRepo.add(newUser);
	}

	/**
	 * Add a book to the cart of a customer.
	 *
	 * @param addCartDto A DTO containing the username of the customer and the title of the book.
	 * @return A Result indicating whether the operation was successful. If the operation was
	 *         unsuccessful, the contained exception will be a subclass of
	 *         {@link application.exceptions.businessexceptions.cartexceptions.CartException}.
	 */
	public Result<User> addCart(AddCartDto addCartDto) {
		Result<User> userSearchResult = isCustomer(addCartDto.username());
		if (!userSearchResult.isSuccessful())
			return new Result<>(userSearchResult);
		Customer user = (Customer) userSearchResult.getData();

		Result<Book> bookSearchResult = bookRepo.get(addCartDto.title());
		if (bookSearchResult.isFailure())
			return new Result<>(bookSearchResult);
		Book book = bookSearchResult.getData();

		if (!user.canAddBook(book))
			return Result.failure(new CantAddToCart(user.getAddBookError(book)));

		user.addBook(book);
		return Result.success(user);
	}

	/**
	 * Remove a book from the cart of a customer.
	 *
	 * @param removeCartDto A DTO containing the username of the customer and the title of the book to be removed.
	 * @return A Result indicating whether the operation was successful. If the operation was
	 *         unsuccessful, the contained exception will be a subclass of
	 *         {@link application.exceptions.businessexceptions.cartexceptions.CartException}.
	 */
	public Result<User> RemoveCart(RemoveCartDto removeCartDto) {
		Result<User> userSearchResult = isCustomer(removeCartDto.username());
		if (!userSearchResult.isSuccessful())
			return new Result<>(userSearchResult);
		Customer user = (Customer) userSearchResult.getData();

		Result<Book> bookSearchResult = bookRepo.get(removeCartDto.title());
		if (bookSearchResult.isFailure())
			return new Result<>(bookSearchResult);
		Book book = bookSearchResult.getData();

		if (!user.canRemoveBook(book))
			return Result.failure(new CantRemoveFromCart(user.getRemoveBookError(book)));

		user.removeBook(book);
		return Result.success(user);
	}

	/**
	 * Adds credit to a customer.
	 *
	 * @param addCreditDto A DTO containing the username of the customer and the amount of credit to add.
	 * @return A Result indicating whether the operation was successful. If the operation was
	 *         unsuccessful, the contained exception will be a subclass of
	 *         {@link application.exceptions.businessexceptions.userexceptions.UserException}.
	 */
	public Result<User> addCredit(AddCreditDto addCreditDto) {
		Result<User> userSearchResult = isCustomer(addCreditDto.username());
		if (!userSearchResult.isSuccessful())
			return new Result<>(userSearchResult);
		Customer user = (Customer) userSearchResult.getData();

		user.addCredit(addCreditDto.credit());
		return Result.success(user);
	}

	/**
	 * Check if a user with the given username exists and is a customer.
	 *
	 * @param username The username of the user to check.
	 * @return A Result indicating whether the operation was successful. If the operation was
	 *         unsuccessful, the contained exception will be a subclass of
	 *         {@link application.exceptions.businessexceptions.BusinessException}. The only
	 *         possible exception is an {@link application.exceptions.businessexceptions.userexceptions.InvalidAccess}.
	 */
	private Result<User> isCustomer(String username) {
		Result<User> userSearchResult = doesExist(username);
		if (userSearchResult.isFailure())
			return new Result<>(userSearchResult);
		else if (userSearchResult.getData().getRole() != User.Role.CUSTOMER)
			return Result.failure(new InvalidAccess("customer"));

		return userSearchResult;
	}

	/**
	 * Check if a user with the given username exists.
	 *
	 * @param username The username of the user to check.
	 * @return A Result indicating whether the operation was successful. If the operation was
	 *         unsuccessful, the contained exception will be a subclass of
	 *         {@link application.exceptions.businessexceptions.BusinessException}. The only
	 *         possible exception is an {@link application.exceptions.businessexceptions.userexceptions.UserDoesNotExists}.
	 */
	Result<User> doesExist(String username) {
		return userRepo.get(username);
	}
}
