package application.services;

import application.dtos.AddCartDto;
import application.dtos.AddUserDto;
import application.exceptions.businessexceptions.cartexceptions.CantAddToCart;
import application.exceptions.businessexceptions.userexceptions.InvalidAccess;
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
			return Result.failure(new CantAddToCart(user.getAddBookError()));

		user.addBook(book);
		return Result.success(user);
	}

	private Result<User> isCustomer(String username) {
		Result<User> userSearchResult = doesExist(username);
		if (userSearchResult.isFailure())
			return new Result<>(userSearchResult);
		else if (userSearchResult.getData().getRole() != User.Role.CUSTOMER)
			return Result.failure(new InvalidAccess("customer"));

		return userSearchResult;
	}

	Result<User> doesExist(String username) {
		return userRepo.get(username);
	}
}
