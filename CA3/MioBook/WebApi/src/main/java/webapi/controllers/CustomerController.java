package webapi.controllers;

import application.result.Result;
import application.uscase.UseCaseType;
import application.uscase.customer.*;
import domain.entities.Customer;
import domain.valueobjects.Cart;
import domain.valueobjects.PurchaseHistory;
import domain.valueobjects.PurchasedCartSummary;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webapi.services.AuthenticationService;
import webapi.services.UseCaseService;
import webapi.views.customer.PurchaseHistoryView;



@RequiredArgsConstructor
@RestController
@RequestMapping("/customer") // TODO: maybe my or profile?
public class CustomerController {

	private final UseCaseService useCaseService;
	private final AuthenticationService authenticationService;

	@PostMapping("/cart")
	public ResponseEntity<String> addCart(@Valid @RequestBody AddCartUseCase.AddCartData data) {
		authenticationService.validateSomeOneLoggedIn();

		AddCartUseCase useCase = (AddCartUseCase) useCaseService.getUseCase(UseCaseType.ADD_CART);
		Result<Customer> result = useCase.perform(data, authenticationService.getUserName(), authenticationService.getUserRole());
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok("Added book to cart.");
	}

	@GetMapping("/cart")
	public ResponseEntity<Cart> getCart() {
		authenticationService.validateSomeOneLoggedIn();

		GetCartUseCase useCase = (GetCartUseCase) useCaseService.getUseCase(UseCaseType.GET_CART);
		Result<Cart> result = useCase.perform(authenticationService.getUserName(), authenticationService.getUserRole());
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok(result.getData());
	}

	@GetMapping("/purchaseHistory")
	public ResponseEntity<PurchaseHistoryView> getPurchaseHistory() {
		authenticationService.validateSomeOneLoggedIn();

		GetPurchaseHistoryUseCase useCase = (GetPurchaseHistoryUseCase) useCaseService.getUseCase(UseCaseType.GET_PURCHASE_HISTORY);
		Result<PurchaseHistory> result = useCase.perform(authenticationService.getUserName(), authenticationService.getUserRole());
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok(new PurchaseHistoryView(result.getData()));
	}

	@DeleteMapping("/cart/{title}")
	public ResponseEntity<String> removeCart(@NotBlank @PathVariable String title) {
		authenticationService.validateSomeOneLoggedIn();

		RemoveCartUseCase useCase = (RemoveCartUseCase) useCaseService.getUseCase(UseCaseType.REMOVE_CART);
		Result<Customer> result = useCase.perform(title, authenticationService.getUserName(), authenticationService.getUserRole());
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok("Removed book from cart.");
	}

	@PatchMapping("credit")
	public ResponseEntity<String> increaseCredit(@Valid @RequestBody AddCreditUseCase.AddCreditData data) {
		authenticationService.validateSomeOneLoggedIn();

		AddCreditUseCase useCase = (AddCreditUseCase) useCaseService.getUseCase(UseCaseType.ADD_CREDIT);
		Result<Customer> result = useCase.perform(data, authenticationService.getUserName(), authenticationService.getUserRole());
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok("Credit added successfully.");
	}

	@PostMapping("/purchase")
	public ResponseEntity<PurchasedCartSummary> purchaseCart() {
		authenticationService.validateSomeOneLoggedIn();

		PurchaseCartUseCase useCase = (PurchaseCartUseCase) useCaseService.getUseCase(UseCaseType.PURCHASE_CART);
		Result<PurchasedCartSummary> result = useCase.perform(authenticationService.getUserName(), authenticationService.getUserRole());
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok(result.getData());
	}

	@PostMapping("/borrow")
	public ResponseEntity<String> borrowBook(@Valid @RequestBody BorrowBookUseCase.BorrowBookData data) {
		authenticationService.validateSomeOneLoggedIn();

		BorrowBookUseCase useCase = (BorrowBookUseCase) useCaseService.getUseCase(UseCaseType.BORROW_BOOK);
		Result<Customer> result = useCase.perform(data, authenticationService.getUserName(), authenticationService.getUserRole());
		if (result.isFailure())
			throw result.getException();

		return ResponseEntity.ok("Added borrowed book to cart.");
	}
}
