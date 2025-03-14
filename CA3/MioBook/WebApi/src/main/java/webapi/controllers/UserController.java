package webapi.controllers;

import application.result.Result;
import application.uscase.UseCaseType;
import application.uscase.customer.AddCartUseCase;
import application.uscase.customer.AddCreditUseCase;
import application.uscase.customer.RemoveCartUseCase;
import application.uscase.user.AddUserUseCase;
import domain.entities.Customer;
import domain.entities.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import webapi.services.AuthenticationService;
import webapi.services.UseCaseService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/user")
public class UserController {
    private final UseCaseService useCaseService;
    private final AuthenticationService authenticationService;

    @PostMapping
    public ResponseEntity<String> addUser(@Valid @RequestBody AddUserUseCase.AddUserData data) {
        AddUserUseCase useCase = (AddUserUseCase) useCaseService.getUseCase(UseCaseType.ADD_USER);
        Result<User> result = useCase.perform(data);
        if (result.isFailure())
            throw result.getException();

        return ResponseEntity.ok("User added successfully.");
    }

    @PostMapping("/cart")
    public ResponseEntity<String> addCart(@Valid @RequestBody AddCartUseCase.AddCartData data) {
        AddCartUseCase useCase = (AddCartUseCase) useCaseService.getUseCase(UseCaseType.ADD_CART);
        Result<Customer> result = useCase.perform(data, authenticationService.getUserName(), authenticationService.getUserRole());
        if (result.isFailure())
            throw result.getException();

        return ResponseEntity.ok("Added book to cart.");
    }

    @DeleteMapping("/cart")
    public ResponseEntity<String> removeCart(@Valid @RequestBody RemoveCartUseCase.RemoveCartData data) {
        RemoveCartUseCase useCase = (RemoveCartUseCase) useCaseService.getUseCase(UseCaseType.REMOVE_CART);
        Result<Customer> result = useCase.perform(data, authenticationService.getUserName(), authenticationService.getUserRole());
        if (result.isFailure())
            throw result.getException();

        return ResponseEntity.ok("Removed book from cart.");
    }

    @PutMapping("credit")
    public ResponseEntity<String> increaseCredit(@Valid @RequestBody AddCreditUseCase.AddCreditData data) {
        AddCreditUseCase useCase = (AddCreditUseCase) useCaseService.getUseCase(UseCaseType.ADD_CREDIT);
        Result<Customer> result = useCase.perform(data, authenticationService.getUserName(), authenticationService.getUserRole());
        if (result.isFailure())
            throw result.getException();

        return ResponseEntity.ok("Credit added successfully.");
    }
}
