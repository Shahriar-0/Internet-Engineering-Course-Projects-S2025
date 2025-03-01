package application.services;

import application.dtos.*;
import application.exceptions.businessexceptions.cartexceptions.*;
import application.exceptions.businessexceptions.userexceptions.*;
import application.repositories.IAuthorRepository;
import application.repositories.IBookRepository;
import application.repositories.IUserRepository;
import application.result.Result;
import application.validators.UserValidator;
import domain.entities.*;
import domain.valueobjects.*;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {

	// FIXME: i think we should have a customer service as well and separate it

	private final IUserRepository userRepository;
	private final UserValidator userValidator;
	private final IBookRepository bookRepository;
	private final IAuthorRepository authorRepository;


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
		return userRepository.add(newUser);
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

		Result<Book> bookSearchResult = bookRepository.get(addCartDto.title());
		if (bookSearchResult.isFailure())
			return new Result<>(bookSearchResult);
		Book book = bookSearchResult.getData();

		if (!user.canAddBook(book))
			return Result.failure(new CantAddToCart(user.findAddBookErrors(book)));

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

		Result<Book> bookSearchResult = bookRepository.get(removeCartDto.title());
		if (bookSearchResult.isFailure())
			return new Result<>(bookSearchResult);
		Book book = bookSearchResult.getData();

		if (!user.canRemoveBook(book))
			return Result.failure(new CantRemoveFromCart(user.findRemoveBookErrors(book)));

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
	 * Purchases the cart of a customer.
	 *
	 * @param purchaseCartDto A DTO containing the username of the customer.
	 * @return A Result indicating whether the operation was successful. If the operation was
	 *         unsuccessful, the contained exception will be a subclass of
	 *         {@link application.exceptions.businessexceptions.cartexceptions.CartException}.
	 */
	public Result<PurchasedCartSummary> purchaseCart(PurchaseCartDto purchaseCartDto) {
		Result<User> userSearchResult = isCustomer(purchaseCartDto.username());
		if (!userSearchResult.isSuccessful())
			return new Result<>(userSearchResult);
		Customer user = (Customer) userSearchResult.getData();

		if (!user.canPurchaseCart())
			return Result.failure(new CantPurchaseCart(user.findPurchaseCartErrors()));

		PurchasedCart purchasedCart = user.purchaseCart();
		return Result.success(new PurchasedCartSummary(purchasedCart));
	}

	/**
	 * Allows a customer to borrow a book for a specified number of days.
	 *
	 * @param borrowBookDto A DTO containing the username of the customer, the title of the book,
	 *                      and the number of days the book will be borrowed.
	 * @return A Result indicating whether the operation was successful. If the operation was
	 *         unsuccessful, the contained exception will be a subclass of
	 *         {@link application.exceptions.businessexceptions.cartexceptions.CartException}.
	 */
	public Result<User> borrowBook(BorrowBookDto borrowBookDto) {
		Result<User> userSearchResult = isCustomer(borrowBookDto.username());
		if (!userSearchResult.isSuccessful())
			return new Result<>(userSearchResult);
		Customer user = (Customer) userSearchResult.getData();

		Result<Book> bookSearchResult = bookRepository.get(borrowBookDto.title());
		if (bookSearchResult.isFailure())
			return new Result<>(bookSearchResult);
		Book book = bookSearchResult.getData();

		if (!user.canAddBook(book))
			return Result.failure(new CantAddToCart(user.findAddBookErrors(book))); // for now their validations are the same

		user.borrowBook(book, borrowBookDto.borrowedDays());
		return Result.success(user);
	}

	/**
	 * Allows a customer to add a review to a book.
	 *
	 * @param addReviewDto A DTO containing the username of the customer, the title of the book,
	 *                     the rating of the book, and the comment of the review.
	 * @return A Result indicating whether the operation was successful. If the operation was
	 *         unsuccessful, the contained exception will be a subclass of
	 *         {@link application.exceptions.businessexceptions.BusinessException}. The only
	 *         possible exceptions are an {@link application.exceptions.businessexceptions.userexceptions.InvalidAccess} if the user is not a customer, a
	 *         {@link application.exceptions.businessexceptions.bookexceptions.BookDoesNotExist} if the book does not exist, or a
	 *         {@link application.exceptions.businessexceptions.reviews.exceptions.ReviewDoesNotExist} if the review does not exist.
	 */
	public Result<Book> addReview(AddReviewDto addReviewDto) {
		Result<User> userSearchResult = isCustomer(addReviewDto.username());
		if (!userSearchResult.isSuccessful())
			return new Result<>(userSearchResult);

		Result<Book> bookSearchResult = bookRepository.get(addReviewDto.title());
		if (bookSearchResult.isFailure())
			return new Result<>(bookSearchResult);

		Book book = bookSearchResult.getData();
		Review review = UtilService.createReview(addReviewDto);
		book.addReview(review);
		return Result.success(book);
	}

	/**
	 * Shows the cart of a customer.
	 *
	 * @param showCartDto A DTO containing the username of the customer to show the cart of.
	 * @return A Result indicating whether the operation was successful. If the operation was
	 *         unsuccessful, the contained exception will be a subclass of
	 *         {@link application.exceptions.businessexceptions.userexceptions.UserException}. The only
	 *         possible exceptions are an {@link application.exceptions.businessexceptions.userexceptions.InvalidAccess} if the user is not a customer, or a
	 *         {@link application.exceptions.businessexceptions.userexceptions.UserDoesNotExists} if the user does not exist.
	 */
	public Result<Cart> showCart(ShowCartDto showCartDto) {
		Result<User> userSearchResult = isCustomer(showCartDto.username());
		if (!userSearchResult.isSuccessful())
			return new Result<>(userSearchResult);
		Customer user = (Customer) userSearchResult.getData();
		return Result.success(user.getCart());
	}

	/**
	 * Shows the purchase history of a customer.
	 *
	 * @param showPurchaseHistoryDto A DTO containing the username of the customer whose purchase history is to be shown.
	 * @return A Result indicating whether the operation was successful. If the operation was
	 *         unsuccessful, the contained exception will be a subclass of
	 *         {@link application.exceptions.businessexceptions.userexceptions.UserException}.
	 */
	public Result<PurchaseHistory> showPurchaseHistory(ShowPurchaseHistoryDto showPurchaseHistoryDto) {
		Result<User> userSearchResult = isCustomer(showPurchaseHistoryDto.username());
		if (!userSearchResult.isSuccessful())
			return new Result<>(userSearchResult);
		Customer user = (Customer) userSearchResult.getData();
		return Result.success(user.getPurchaseHistory());
	}

	/**
	 * Shows the details of a user.
	 *
	 * @param showUserDetailsDto A DTO containing the username of the user to show the details of.
	 * @return A Result indicating whether the operation was successful. If the operation was
	 *         unsuccessful, the contained exception will be a subclass of
	 *         {@link application.exceptions.businessexceptions.userexceptions.UserException}.
	 */
	public Result<User> showUserDetails(ShowUserDetailsDto showUserDetailsDto) {
		Result<User> userSearchResult = doesExist(showUserDetailsDto.username());
		if (userSearchResult.isFailure())
			return new Result<>(userSearchResult);
		return Result.success(userSearchResult.getData());
	}

	/**
	 * Shows the details of an author.
	 *
	 * @param showAuthorDetailsDto A DTO containing the name of the author to show the details of.
	 * @return A Result indicating whether the operation was successful. If the operation was
	 *         unsuccessful, the contained exception will be a subclass of
	 *         {@link application.exceptions.businessexceptions.authorexceptions.AuthorException}.
	 */
	public Result<Author> showAuthorDetails(ShowAuthorDetailsDto showAuthorDetailsDto) {
		Result<Author> authorSearchResult = authorRepository.get(showAuthorDetailsDto.name());
		if (authorSearchResult.isFailure())
			return new Result<>(authorSearchResult);
		return Result.success(authorSearchResult.getData());
	}

	/**
	 * Shows the details of a book.
	 *
	 * @param showBookDetailsDto A DTO containing the title of the book to show the details of.
	 * @return A Result indicating whether the operation was successful. If the operation was
	 *         unsuccessful, the contained exception will be a subclass of
	 *         {@link application.exceptions.businessexceptions.bookexceptions.BookException}.
	 */
	public Result<Book> showBookDetails(ShowBookDetailsDto showBookDetailsDto) {
		Result<Book> bookSearchResult = bookRepository.get(showBookDetailsDto.title());
		if (bookSearchResult.isFailure())
			return new Result<>(bookSearchResult);
		return Result.success(bookSearchResult.getData());
	}

	public Result<PurchasedBooks> showPurchasedBooks(ShowPurchasedBooksDto showPurchasedBooksDto) {
		Result<User> userSearchResult = isCustomer(showPurchasedBooksDto.username());
		if (!userSearchResult.isSuccessful())
			return new Result<>(userSearchResult);
		Customer user = (Customer) userSearchResult.getData();
		return Result.success(user.getPurchasedBooks());
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
		return userRepository.get(username);
	}
}
