package webapi.controllers;

import application.result.Result;
import application.usecase.UseCaseType;
import application.usecase.customer.book.GetPurchasedBooks;
import application.usecase.customer.cart.AddCart;
import application.usecase.customer.cart.BorrowBook;
import application.usecase.customer.cart.GetCart;
import application.usecase.customer.cart.RemoveCart;
import application.usecase.customer.purchase.GetPurchaseHistory;
import application.usecase.customer.purchase.PurchaseCart;
import application.usecase.customer.wallet.AddCredit;
import domain.entities.Customer;
import domain.valueobjects.Cart;
import domain.valueobjects.PurchaseHistory;
import domain.valueobjects.PurchasedBooks;
import domain.valueobjects.PurchasedCartSummary;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import webapi.response.Response;
import webapi.services.AuthenticationService;
import webapi.services.UseCaseService;
import webapi.views.customer.CartView;
import webapi.views.customer.PurchaseHistoryView;
import webapi.views.customer.PurchasedBooksView;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


@RequiredArgsConstructor
@RestController
@RequestMapping("/profile")
public class CustomerController {

	private final UseCaseService useCaseService;
	private final AuthenticationService authenticationService;

	@PostMapping("/cart")
	public Response<?> addCart(@Valid @RequestBody AddCart.AddCartData data) {
		authenticationService.validateSomeOneLoggedIn();

		AddCart useCase = (AddCart) useCaseService.getUseCase(UseCaseType.ADD_CART);
		Result<Customer> result = useCase.perform(data, authenticationService.getUserName(), authenticationService.getUserRole());
		if (result.isFailure())
			throw result.exception();

		return Response.of(CREATED, "Added book to cart.");
	}

	@GetMapping("/cart")
	public Response<CartView> getCart() {
		authenticationService.validateSomeOneLoggedIn();

		GetCart useCase = (GetCart) useCaseService.getUseCase(UseCaseType.GET_CART);
		Result<Cart> result = useCase.perform(authenticationService.getUserName(), authenticationService.getUserRole()); // FIXME: clean this authentication
		if (result.isFailure())
			throw result.exception();

		return Response.of(new CartView(result.data()), OK);
	}

	@GetMapping("/history")
	public Response<PurchaseHistoryView> getPurchaseHistory() {
		authenticationService.validateSomeOneLoggedIn();

		GetPurchaseHistory useCase = (GetPurchaseHistory) useCaseService.getUseCase(UseCaseType.GET_PURCHASE_HISTORY);
		Result<PurchaseHistory> result = useCase.perform(authenticationService.getUserName(), authenticationService.getUserRole());
		if (result.isFailure())
			throw result.exception();

		return Response.of(new PurchaseHistoryView(result.data()), OK);
	}

	@GetMapping("/books")
	public Response<PurchasedBooksView> getPurchasedBooks() {
		authenticationService.validateSomeOneLoggedIn();

		GetPurchasedBooks useCase = (GetPurchasedBooks) useCaseService.getUseCase(UseCaseType.GET_PURCHASED_BOOKS);
		Result<PurchasedBooks> result = useCase.perform(authenticationService.getUserName(), authenticationService.getUserRole());
		if (result.isFailure())
			throw result.exception();

		return Response.of(new PurchasedBooksView(result.data()), OK);
	}

	@DeleteMapping("/cart/{title}")
	public Response<?> removeCart(@NotBlank @PathVariable String title) {
		authenticationService.validateSomeOneLoggedIn();

		RemoveCart useCase = (RemoveCart) useCaseService.getUseCase(UseCaseType.REMOVE_CART);
		Result<Customer> result = useCase.perform(title, authenticationService.getUserName(), authenticationService.getUserRole());
		if (result.isFailure())
			throw result.exception();

		return Response.of(OK, "Removed book from cart.");
	}

	@PatchMapping("credit")
	public Response<?> increaseCredit(@Valid @RequestBody AddCredit.AddCreditData data) {
		authenticationService.validateSomeOneLoggedIn();

		AddCredit useCase = (AddCredit) useCaseService.getUseCase(UseCaseType.ADD_CREDIT);
		Result<Customer> result = useCase.perform(data, authenticationService.getUserName(), authenticationService.getUserRole());
		if (result.isFailure())
			throw result.exception();

		return Response.of(OK, "Credit added successfully.");
	}

	@PostMapping("/purchase")
	public Response<PurchasedCartSummary> purchaseCart() {
		authenticationService.validateSomeOneLoggedIn();

		PurchaseCart useCase = (PurchaseCart) useCaseService.getUseCase(UseCaseType.PURCHASE_CART);
		Result<PurchasedCartSummary> result = useCase.perform(authenticationService.getUserName(), authenticationService.getUserRole());
		if (result.isFailure())
			throw result.exception();

		return Response.of(result.data(), CREATED);
	}

	@PostMapping("/borrow")
	public Response<?> borrowBook(@Valid @RequestBody BorrowBook.BorrowBookData data) {
		authenticationService.validateSomeOneLoggedIn();

		BorrowBook useCase = (BorrowBook) useCaseService.getUseCase(UseCaseType.BORROW_BOOK);
		Result<Customer> result = useCase.perform(data, authenticationService.getUserName(), authenticationService.getUserRole());
		if (result.isFailure())
			throw result.exception();

		return Response.of(CREATED, "Added borrowed book to cart.");
	}
}
