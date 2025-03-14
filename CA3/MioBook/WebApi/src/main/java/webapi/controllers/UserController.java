package webapi.controllers;

import application.result.Result;
import application.uscase.UseCaseType;
import application.uscase.customer.AddCartUseCase;
import application.uscase.user.AddUserUseCase;
import domain.entities.Customer;
import domain.entities.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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
}
