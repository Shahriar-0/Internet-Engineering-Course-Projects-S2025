package application.services;

import application.dtos.AddCartDto;
import application.dtos.AddUserDto;
import application.exceptions.businessexceptions.cartexceptions.CantAddToCart;
import application.exceptions.businessexceptions.userexceptions.InvalidAccess;
import application.repositories.IBookRepository;
import application.repositories.IUserRepository;
import application.result.Result;
import application.validators.UserValidator;
import domain.entities.Book;
import domain.entities.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {

	private final IUserRepository userRepo;
	private final UserValidator userValidator;
	private final IBookRepository bookRepo;

	public Result<User> addUser(AddUserDto newUserDto) {
		Result<AddUserDto> validationResult = userValidator.validate(newUserDto);
		if (validationResult.isFailure())
			return new Result<>(validationResult);

		User newUser = UtilService.createUser(newUserDto);
		newUser.setCredit(0);
		return userRepo.add(newUser);
	}

	public Result<Void> addToCart(AddCartDto addCartDto) {
		Result<User> userSearchResult = isCustomer(addCartDto.username());
		if (!userSearchResult.isSuccessful())
			return new Result<>(userSearchResult);
		User user = userSearchResult.getData();

		Result<Book> bookSearchResult = bookRepo.find(addCartDto.title());
		if (bookSearchResult.isFailure())
			return new Result<>(bookSearchResult);
		Book book = bookSearchResult.getData();

		if (!user.canAddBook(book))
			return Result.failure(new CantAddToCart(user.getAddBookError()));

		user.addBook(book);
		return null;
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
		return userRepo.find(username);
	}
}
