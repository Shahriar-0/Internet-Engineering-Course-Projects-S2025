package webapi.controllers;

import application.result.Result;
import application.usecase.UseCaseType;
import application.usecase.customer.*;
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
	public Response<?> addCart(@Valid @RequestBody AddCartUseCase.AddCartData data) {
		authenticationService.validateSomeOneLoggedIn();

		AddCartUseCase useCase = (AddCartUseCase) useCaseService.getUseCase(UseCaseType.ADD_CART);
		Result<Customer> result = useCase.perform(data, authenticationService.getUserName(), authenticationService.getUserRole());
		if (result.isFailure())
			throw result.getException();

		return Response.of(CREATED, "Added book to cart.");
	}

	@GetMapping("/cart")
	public Response<CartView> getCart() {
		authenticationService.validateSomeOneLoggedIn();

		GetCartUseCase useCase = (GetCartUseCase) useCaseService.getUseCase(UseCaseType.GET_CART);
		Result<Cart> result = useCase.perform(authenticationService.getUserName(), authenticationService.getUserRole()); // FIXME: clean this authentication
		if (result.isFailure())
			throw result.getException();

		return Response.of(new CartView(result.getData()), OK);
	}

	@GetMapping("/history")
	public Response<PurchaseHistoryView> getPurchaseHistory() {
		authenticationService.validateSomeOneLoggedIn();

		GetPurchaseHistoryUseCase useCase = (GetPurchaseHistoryUseCase) useCaseService.getUseCase(UseCaseType.GET_PURCHASE_HISTORY);
		Result<PurchaseHistory> result = useCase.perform(authenticationService.getUserName(), authenticationService.getUserRole());
		if (result.isFailure())
			throw result.getException();

		return Response.of(new PurchaseHistoryView(result.getData()), OK);
	}

	@GetMapping("/books")
	public Response<PurchasedBooksView> getPurchasedBooks() {
		authenticationService.validateSomeOneLoggedIn();

		GetPurchasedBooksUseCase useCase = (GetPurchasedBooksUseCase) useCaseService.getUseCase(UseCaseType.GET_PURCHASED_BOOKS);
		Result<PurchasedBooks> result = useCase.perform(authenticationService.getUserName(), authenticationService.getUserRole());
		if (result.isFailure())
			throw result.getException();

		return Response.of(new PurchasedBooksView(result.getData()), OK);
	}

	@DeleteMapping("/cart/{title}")
	public Response<?> removeCart(@NotBlank @PathVariable String title) {
		authenticationService.validateSomeOneLoggedIn();

		RemoveCartUseCase useCase = (RemoveCartUseCase) useCaseService.getUseCase(UseCaseType.REMOVE_CART);
		Result<Customer> result = useCase.perform(title, authenticationService.getUserName(), authenticationService.getUserRole());
		if (result.isFailure())
			throw result.getException();

		return Response.of(OK, "Removed book from cart.");
	}

	@PatchMapping("credit")
	public Response<?> increaseCredit(@Valid @RequestBody AddCreditUseCase.AddCreditData data) {
		authenticationService.validateSomeOneLoggedIn();

		AddCreditUseCase useCase = (AddCreditUseCase) useCaseService.getUseCase(UseCaseType.ADD_CREDIT);
		Result<Customer> result = useCase.perform(data, authenticationService.getUserName(), authenticationService.getUserRole());
		if (result.isFailure())
			throw result.getException();

		return Response.of(OK, "Credit added successfully.");
	}

	@PostMapping("/purchase")
	public Response<PurchasedCartSummary> purchaseCart() {
		authenticationService.validateSomeOneLoggedIn();

		PurchaseCartUseCase useCase = (PurchaseCartUseCase) useCaseService.getUseCase(UseCaseType.PURCHASE_CART);
		Result<PurchasedCartSummary> result = useCase.perform(authenticationService.getUserName(), authenticationService.getUserRole());
		if (result.isFailure())
			throw result.getException();

		return Response.of(result.getData(), CREATED);
	}

	@PostMapping("/borrow")
	public Response<?> borrowBook(@Valid @RequestBody BorrowBookUseCase.BorrowBookData data) {
		authenticationService.validateSomeOneLoggedIn();

		BorrowBookUseCase useCase = (BorrowBookUseCase) useCaseService.getUseCase(UseCaseType.BORROW_BOOK);
		Result<Customer> result = useCase.perform(data, authenticationService.getUserName(), authenticationService.getUserRole());
		if (result.isFailure())
			throw result.getException();

		return Response.of(CREATED, "Added borrowed book to cart.");
	}
}
