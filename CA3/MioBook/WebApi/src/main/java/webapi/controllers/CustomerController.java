package webapi.controllers;

import application.result.Result;
import application.uscase.UseCaseType;
import application.uscase.customer.*;
import domain.entities.Customer;
import domain.valueobjects.PurchasedCartSummary;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webapi.services.AuthenticationService;
import webapi.services.UseCaseService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/customer")
public class CustomerController {
    private final UseCaseService useCaseService;
    private final AuthenticationService authenticationService;

    @PostMapping("/cart")
    public ResponseEntity<String> addCart(@Valid @RequestBody AddCartUseCase.AddCartData data) {
        AddCartUseCase useCase = (AddCartUseCase) useCaseService.getUseCase(UseCaseType.ADD_CART);
        Result<Customer> result = useCase.perform(data, authenticationService.getUserName(), authenticationService.getUserRole());
        if (result.isFailure())
            throw result.getException();

        return ResponseEntity.ok("Added book to cart.");
    }

    @DeleteMapping("/cart/{title}")
    public ResponseEntity<String> removeCart(@NotBlank(message = "Title is required") @PathVariable String title) {
        RemoveCartUseCase useCase = (RemoveCartUseCase) useCaseService.getUseCase(UseCaseType.REMOVE_CART);
        Result<Customer> result = useCase.perform(title, authenticationService.getUserName(), authenticationService.getUserRole());
        if (result.isFailure())
            throw result.getException();

        return ResponseEntity.ok("Removed book from cart.");
    }

    @PatchMapping("credit")
    public ResponseEntity<String> increaseCredit(@Valid @RequestBody AddCreditUseCase.AddCreditData data) {
        AddCreditUseCase useCase = (AddCreditUseCase) useCaseService.getUseCase(UseCaseType.ADD_CREDIT);
        Result<Customer> result = useCase.perform(data, authenticationService.getUserName(), authenticationService.getUserRole());
        if (result.isFailure())
            throw result.getException();

        return ResponseEntity.ok("Credit added successfully.");
    }

    @PostMapping("/purchase")
    public ResponseEntity<PurchasedCartSummary> purchaseCart() {
        PurchaseCartUseCase useCase = (PurchaseCartUseCase) useCaseService.getUseCase(UseCaseType.PURCHASE_CART);
        Result<PurchasedCartSummary> result = useCase.perform(authenticationService.getUserName(), authenticationService.getUserRole());
        if (result.isFailure())
            throw result.getException();

        return ResponseEntity.ok(result.getData());
    }

    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@Valid @RequestBody BorrowBookUseCase.BorrowBookData data) {
        BorrowBookUseCase useCase = (BorrowBookUseCase) useCaseService.getUseCase(UseCaseType.BORROW_BOOK);
        Result<Customer> result = useCase.perform(data, authenticationService.getUserName(), authenticationService.getUserRole());
        if (result.isFailure())
            throw result.getException();

        return ResponseEntity.ok("Added borrowed book to cart.");
    }
}
