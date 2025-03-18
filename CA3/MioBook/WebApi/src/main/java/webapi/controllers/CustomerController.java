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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import webapi.services.AuthenticationService;
import webapi.services.UseCaseService;
import webapi.views.customer.PurchaseHistoryView;
import webapi.views.customer.PurchasedBooksView;


@RequiredArgsConstructor
@RestController
@RequestMapping("/profile")
public class CustomerController {

	private final UseCaseService useCaseService;
	private final AuthenticationService authenticationService;

	@PostMapping("/cart")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<String> addCart(@Valid @RequestBody AddCartUseCase.AddCartData data) {
		AddCartUseCase useCase = (AddCartUseCase) useCaseService.getUseCase(UseCaseType.ADD_CART);
		String username = authenticationService.getUserName();
		Result<Customer> result = useCase.perform(data, username);
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok("Added book to cart.");
	}

	@GetMapping("/cart")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<Cart> getCart() {
		GetCartUseCase useCase = (GetCartUseCase) useCaseService.getUseCase(UseCaseType.GET_CART);
		String username = authenticationService.getUserName();
		Result<Cart> result = useCase.perform(username);
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok(result.getData());
	}

	@GetMapping("/history")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<PurchaseHistoryView> getPurchaseHistory() {
		GetPurchaseHistoryUseCase useCase = (GetPurchaseHistoryUseCase) useCaseService.getUseCase(UseCaseType.GET_PURCHASE_HISTORY);
		String username = authenticationService.getUserName();
		Result<PurchaseHistory> result = useCase.perform(username);
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok(new PurchaseHistoryView(result.getData()));
	}

	@GetMapping("/books")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<PurchasedBooksView> getPurchasedBooks() {
		GetPurchasedBooksUseCase useCase = (GetPurchasedBooksUseCase) useCaseService.getUseCase(UseCaseType.GET_PURCHASED_BOOKS);
		String username = authenticationService.getUserName();
		Result<PurchasedBooks> result = useCase.perform(username);
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok(new PurchasedBooksView(result.getData()));
	}

	@DeleteMapping("/cart/{title}")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<String> removeCart(@NotBlank @PathVariable String title) {
		RemoveCartUseCase useCase = (RemoveCartUseCase) useCaseService.getUseCase(UseCaseType.REMOVE_CART);
		String username = authenticationService.getUserName();
		Result<Customer> result = useCase.perform(title, username);
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok("Removed book from cart.");
	}

	@PatchMapping("credit")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<String> increaseCredit(@Valid @RequestBody AddCreditUseCase.AddCreditData data) {
		AddCreditUseCase useCase = (AddCreditUseCase) useCaseService.getUseCase(UseCaseType.ADD_CREDIT);
		String username = authenticationService.getUserName();
		Result<Customer> result = useCase.perform(data, username);
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok("Credit added successfully.");
	}

	@PostMapping("/purchase")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<PurchasedCartSummary> purchaseCart() {
		PurchaseCartUseCase useCase = (PurchaseCartUseCase) useCaseService.getUseCase(UseCaseType.PURCHASE_CART);
		String username = authenticationService.getUserName();
		Result<PurchasedCartSummary> result = useCase.perform(username);
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok(result.getData());
	}

	@PostMapping("/borrow")
	@PreAuthorize("hasRole('CUSTOMER')")
	public ResponseEntity<String> borrowBook(@Valid @RequestBody BorrowBookUseCase.BorrowBookData data) {
		BorrowBookUseCase useCase = (BorrowBookUseCase) useCaseService.getUseCase(UseCaseType.BORROW_BOOK);
		String username = authenticationService.getUserName();
		Result<Customer> result = useCase.perform(data, username);
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok("Added borrowed book to cart.");
	}
}
