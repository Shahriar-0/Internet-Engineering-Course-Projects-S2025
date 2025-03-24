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
import domain.entities.booklicense.BookLicense;
import domain.entities.user.Customer;
import domain.entities.cart.Cart;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import webapi.accesscontrol.Access;
import webapi.response.Response;
import webapi.services.AuthenticationService;
import webapi.services.UseCaseService;
import webapi.views.customer.CartView;
import webapi.views.customer.PurchaseHistoryView;
import webapi.views.customer.PurchasedBooksView;
import webapi.views.customer.PurchasedCartSummaryView;

import java.util.List;

import static domain.entities.user.Role.CUSTOMER;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;


@RequiredArgsConstructor
@RestController
@RequestMapping("/profile")
public class CustomerController {

    private static final String ADD_BOOK_TO_CART_MESSAGE = "Added book to cart.";
    private static final String REMOVE_BOOK_FROM_CART_MESSAGE = "Removed book from cart.";

	private final UseCaseService useCaseService;
	private final AuthenticationService authenticationService;

	@PostMapping("/cart")
	@Access(roles = {CUSTOMER})
	public Response<?> addCart(@Valid @RequestBody AddCart.AddCartData data) {
		AddCart useCase = (AddCart) useCaseService.getUseCase(UseCaseType.ADD_CART);

		Result<Customer> result = useCase.perform(data, authenticationService.getUser());
		if (result.isFailure())
			throw result.exception();

		return Response.of(CREATED, ADD_BOOK_TO_CART_MESSAGE);
	}

	@GetMapping("/cart")
	@Access(roles = {CUSTOMER})
	public Response<CartView> getCart() {
		GetCart useCase = (GetCart) useCaseService.getUseCase(UseCaseType.GET_CART);

		Result<Cart> result = useCase.perform(authenticationService.getUser());
		if (result.isFailure())
			throw result.exception();

		return Response.of(new CartView(result.data()), OK);
	}

	@GetMapping("/history")
	@Access(roles = {CUSTOMER})
	public Response<PurchaseHistoryView> getPurchaseHistory() {
		GetPurchaseHistory useCase = (GetPurchaseHistory) useCaseService.getUseCase(UseCaseType.GET_PURCHASE_HISTORY);

		Result<List<Cart>> result = useCase.perform(authenticationService.getUser());
		if (result.isFailure())
			throw result.exception();

		return Response.of(new PurchaseHistoryView(result.data(), authenticationService.getUser().getUsername()), OK);
	}

	@GetMapping("/books")
	@Access(roles = {CUSTOMER})
	public Response<PurchasedBooksView> getPurchasedBooks() {
		GetPurchasedBooks useCase = (GetPurchasedBooks) useCaseService.getUseCase(UseCaseType.GET_PURCHASED_BOOKS);

		Result<List<BookLicense>> result = useCase.perform(authenticationService.getUser());
		if (result.isFailure())
			throw result.exception();

		return Response.of(PurchasedBooksView.create(result.data()), OK);
	}

	@DeleteMapping("/cart/{title}")
	@Access(roles = {CUSTOMER})
	public Response<?> removeCart(@PathVariable String title) {
		RemoveCart useCase = (RemoveCart) useCaseService.getUseCase(UseCaseType.REMOVE_CART);

		Result<Customer> result = useCase.perform(title, authenticationService.getUser());
		if (result.isFailure())
			throw result.exception();

		return Response.of(OK, REMOVE_BOOK_FROM_CART_MESSAGE);
	}

	@PatchMapping("credit")
	@Access(roles = {CUSTOMER})
	public Response<?> increaseCredit(@Valid @RequestBody AddCredit.AddCreditData data) {
		AddCredit useCase = (AddCredit) useCaseService.getUseCase(UseCaseType.ADD_CREDIT);

		Result<Customer> result = useCase.perform(data, authenticationService.getUser());
		if (result.isFailure())
			throw result.exception();

		return Response.of(OK, "Credit added successfully.");
	}

	@PostMapping("/purchase")
	@Access(roles = {CUSTOMER})
	public Response<PurchasedCartSummaryView> purchaseCart() {
		PurchaseCart useCase = (PurchaseCart) useCaseService.getUseCase(UseCaseType.PURCHASE_CART);

		Result<Cart> result = useCase.perform(authenticationService.getUser());
		if (result.isFailure())
			throw result.exception();

		return Response.of(new PurchasedCartSummaryView(result.data()), CREATED);
	}

	@PostMapping("/borrow")
	@Access(roles = {CUSTOMER})
	public Response<?> borrowBook(@Valid @RequestBody BorrowBook.BorrowBookData data) {
		BorrowBook useCase = (BorrowBook) useCaseService.getUseCase(UseCaseType.BORROW_BOOK);
		
		Result<Customer> result = useCase.perform(data, authenticationService.getUser());
		if (result.isFailure())
			throw result.exception();

		return Response.of(CREATED, "Added borrowed book to cart.");
	}
}
